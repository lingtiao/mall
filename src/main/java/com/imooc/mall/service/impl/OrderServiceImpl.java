package com.imooc.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.mall.common.Constant;
import com.imooc.mall.exception.ImoocMallException;
import com.imooc.mall.exception.ImoocMallExceptionEnum;
import com.imooc.mall.filter.UserFilter;
import com.imooc.mall.model.dao.CartMapper;
import com.imooc.mall.model.dao.OrderItemMapper;
import com.imooc.mall.model.dao.OrderMapper;
import com.imooc.mall.model.dao.ProductMapper;
import com.imooc.mall.model.pojo.Order;
import com.imooc.mall.model.pojo.OrderItem;
import com.imooc.mall.model.pojo.Product;
import com.imooc.mall.model.request.CreateOrderReq;
import com.imooc.mall.model.vo.CartVO;
import com.imooc.mall.model.vo.OrderItemVO;
import com.imooc.mall.model.vo.OrderVO;
import com.imooc.mall.service.CartService;
import com.imooc.mall.service.OrderService;
import com.imooc.mall.utils.OrderCodeFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：订单模块的Service实现类
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    CartService cartService;
    @Autowired
    ProductMapper productMapper;
    @Autowired
    CartMapper cartMapper;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    OrderItemMapper orderItemMapper;


    /**
     * 创建订单
     * @param createOrderReq
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public String create(CreateOrderReq createOrderReq) {
        //首先，拿到用户ID；
        Integer userId = UserFilter.currentUser.getId();
        //从购物车中，查询当前用户的、购物车中的、已经被勾选的商品；
        List<CartVO> cartVOList = cartService.list(userId);

        //遍历查到的购物车数据，从中筛选出被勾选的；
        ArrayList<CartVO> cartVOArrayListTemp = new ArrayList<>();
        for (int i = 0; i < cartVOList.size(); i++) {
            CartVO cartVO =  cartVOList.get(i);
            if (cartVO.getSelected().equals(Constant.CartIsSelected.CHECKED)) {
                cartVOArrayListTemp.add(cartVO);
            }
        }
        cartVOList = cartVOArrayListTemp;
        //如果，购物车中没有已经被勾选的商品：就抛出"购物车勾选的商品为空"异常；
        if (CollectionUtils.isEmpty(cartVOList)) {
            throw new ImoocMallException(ImoocMallExceptionEnum.CART_SELECTED_EMPTY);
        }

        //判断商品是否存在；如果存在，是否是上架状态；商品库存是否足够；
        validSaleStatusAndStock(cartVOList);

        //把【查询购物车表cart表，获得的商品数据】转化为【能够存储到order_item表的、商品数据】
        List<OrderItem> orderItemList = cartVOListToOrderItemList(cartVOList);

        //扣库存;(PS:前面有判断库存的逻辑，程序如果能走到这一步，就说明库存是够的)
        for (int i = 0; i < orderItemList.size(); i++) {
            OrderItem orderItem =  orderItemList.get(i);
            //首先，先拿到原先的product
            Product product = productMapper.selectByPrimaryKey(orderItem.getProductId());
            //然后，计算新的库存；
            int stock = product.getStock() - orderItem.getQuantity();
            if (stock < 0) {//上面已经检查过库存了，这儿又判断，是否是重复工作
                throw new ImoocMallException(ImoocMallExceptionEnum.NOT_ENOUGH);
            }

            product.setStock(product.getStock() - orderItem.getQuantity());
            //然后，去更新库存；也就是扣库存啦；
            productMapper.updateByPrimaryKeySelective(product);

        }
        //把【当前用户的、购物车中已经被勾选的、将要被我们下单的，商品】给删除；也就是，删除cart表中，对应的记录；
        cleanCart(cartVOList);

        //编写逻辑，生成一个订单号
        String orderNum = OrderCodeFactory.getOrderCode(Long.valueOf(userId));
        //创建一个订单；
        Order order = new Order();
        order.setOrderNo(orderNum);//设置订单号
        order.setUserId(userId);//设置用户id
        order.setTotalPrice(totalPrice(orderItemList));//设置订单总价
        order.setReceiverName(createOrderReq.getReceiverName());//设置收件人姓名
        order.setReceiverAddress(createOrderReq.getReceiverAddress());//设置收件人地址
        order.setReceiverMobile(createOrderReq.getReceiverMobile());//设置收件人电话
        order.setOrderStatus(Constant.OrderStatusEnum.NOT_PAY.getCode());//设置订单状态
        order.setPostage(0);//运费；我们这儿目前是包邮
        order.setPaymentType(1);//付款方式；我们这儿只有一种1在线支付
        //把这个订单，添加到order表中，新增一个订单记录；
        orderMapper.insertSelective(order);

        //也要利用循环，把订单中的每种商品，写到order_item表中；
        for (int i = 0; i < orderItemList.size(); i++) {
            OrderItem orderItem =  orderItemList.get(i);
            orderItem.setOrderNo(orderNum);//给其赋上订单号
            orderItemMapper.insertSelective(orderItem);
        }

        //返回结果；
        return orderNum;
    }


    /**
     * 工具方法：判断列表中的商品是否存在、是否是上架状态、库存是否足够；
     * 规则：购物车中的、已经被勾选的商品；但凡有一种不符合要求，都不行；
     * @param cartVOArrayList
     */
    private void validSaleStatusAndStock(List<CartVO> cartVOArrayList) {
        //循环遍历、判断：【购物车中的、已经被勾选的、每一种商品】
        for (int i = 0; i < cartVOArrayList.size(); i++) {
            CartVO cartVO =  cartVOArrayList.get(i);
            //根据【从购物车中，查到的商品信息】，去查product表；
            Product product = productMapper.selectByPrimaryKey(cartVO.getProductId());
            //如果没查到（说明，商品不存在），或者，商品不是上架状态：就抛出"商品状态不可售"异常；
            if (product == null || !product.getStatus().equals(Constant.SaleStatus.SALE)) {
                throw new ImoocMallException(ImoocMallExceptionEnum.NOT_SALE);
            }
            //判断商品库存，如果库存不足，抛出“商品库存不足异常；
            if (cartVO.getQuantity() > product.getStock()) {
                throw new ImoocMallException(ImoocMallExceptionEnum.NOT_ENOUGH);
            }
        }
    }

    /**
     * 工具方法：把【从cart购物车表中，查到的CartVO】转化为【可以存储到order_item表的，OrderItem】；
     * @param cartVOList
     * @return
     */
    private List<OrderItem> cartVOListToOrderItemList(List<CartVO> cartVOList) {
        List<OrderItem> orderItemList = new ArrayList<>();
        for (int i = 0; i < cartVOList.size(); i++) {
            CartVO cartVO = cartVOList.get(i);
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(cartVO.getProductId());
            //下面的，其实是【商品当前的快照信息】
            orderItem.setProductName(cartVO.getProductName());//商品(当前的)名称
            orderItem.setProductImg(cartVO.getProductImage());//商品(当前的)图片
            orderItem.setUnitPrice(cartVO.getPrice());//商品(当前的)单价

            orderItem.setQuantity(cartVO.getQuantity());//该种商品的购买数量
            orderItem.setTotalPrice(cartVO.getTotalPrice());//该种商品的总价

            orderItemList.add(orderItem);
        }
        return orderItemList;
    }

    /**
     * 工具方法：把【当前用户的、购物车中已经被勾选的、将要被我们下单的，商品】给删除；也就是，删除cart表中，对应的记录；
     * @param cartVOList
     */
    private void cleanCart(List<CartVO> cartVOList) {
        for (int i = 0; i < cartVOList.size(); i++) {
            CartVO cartVO =  cartVOList.get(i);
            cartMapper.deleteByPrimaryKey(cartVO.getId());
        }
    }

    /**
     * 工具方法：获取当前订单的总价；也就是该订单中，所用种类商品的总价；
     * @param orderItemList
     * @return
     */
    private Integer totalPrice(List<OrderItem> orderItemList) {
        Integer totalPrice = 0;
        for (int i = 0; i < orderItemList.size(); i++) {
            OrderItem orderItem =  orderItemList.get(i);
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }

    /**
     * 根据订单号，获取订单详情
     * @param orderNo
     * @return
     */
    @Override
    public OrderVO detail(String orderNo) {
        //首先，调用Dao层方法；根据订单号，去查询订单；
        Order order = orderMapper.selectByOrderNo(orderNo);

        //如果根据订单号，没有查到订单，就抛出“订单不存在”异常;
        if (order == null) {
            throw new ImoocMallException(ImoocMallExceptionEnum.NO_ORDER);
        }

        //如果订单存在，还要看下，这个订单是不是属于当前登录用户的；（因为可能存在，A用户去查一个属于B用户的订单：产生横向越权）
        // 如果当前订单不属于当前登录用户，则抛出“订单不属于你”异常；
        Integer userId = UserFilter.currentUser.getId();
        if (!order.getUserId().equals(userId)) {
            throw new ImoocMallException(ImoocMallExceptionEnum.NOT_YOUR_ORDER);
        }

        //调用工具方法：根据订单，按照接口对返回数据的要求，去组织订单详情数据
        OrderVO orderVO = getOrderVO(order);

        return orderVO;
    }

    /**
     * 根据order订单信息，拼装【接口要求的数据格式】：OrderVO
     * @param order
     */
    private OrderVO getOrderVO(Order order) {
        //（1），创建一个OrderVO，然后把【order中，与orderVO中重复的属性，，，复制到orderVO上去】
        OrderVO orderVO = new OrderVO();
        BeanUtils.copyProperties(order, orderVO);

        //（2），获取该订单的orderItemVOList(即，获取该订单中，每种商品的信息)
        //根据orderNo，获取对应的OrderItem信息；
        List<OrderItem> orderItemList = orderItemMapper.selectByOrderNo(order.getOrderNo());
        List<OrderItemVO> orderItemVOList = new ArrayList<>();
        //按照接口对返回数据的要求，把OrderItem转化为OrderItemVO；
        for (int i = 0; i < orderItemList.size(); i++) {
            OrderItem orderItem =  orderItemList.get(i);
            OrderItemVO orderItemVO = new OrderItemVO();
            BeanUtils.copyProperties(orderItem, orderItemVO);
            orderItemVOList.add(orderItemVO);
        }
        //该订单的orderItemVOList，赋值给OrderVO；
        orderVO.setOrderItemVOList(orderItemVOList);

        //（3），获取待订单的订单状态码
        Integer orderStatus = order.getOrderStatus();
        //我们前面把订单状态定义在了枚举类中；借助我们定义的枚举，根据订单状态码获取订单状态的具体内容
        String orderStatusName = Constant.OrderStatusEnum.codeOf(orderStatus).getValue();
        //把订单状态的具体信息，赋值到OrderItem的属性上去
        orderVO.setOrderStatusName(orderStatusName);

        return orderVO;
    }

    /**
     * 前台的，获取某用户的订单列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo listForCustomer(Integer pageNum, Integer pageSize) {
        //首先，获取当前登录用户的userId；
        Integer userId = UserFilter.currentUser.getId();
        //然后，调用Dao层的方法，去查order表，根据userId查询List<order>；
        List<Order> orderList = orderMapper.selectOrderForCustomer(userId);
        //由于接口要求，返回的数据格式，需要是OrderVO；；；所以，编写工具方法：把List<Order>拼装成List<OrderVO>；
        List<OrderVO> orderVOList = orderListToOrderVOList(orderList);
        //然后，设置分页的：当前页和每页条目数
        PageHelper.startPage(pageNum, pageSize);
        //然后，以Mybatis层返回的查询结果List，得到PageInfo对象
        PageInfo pageInfo = new PageInfo<>(orderList);
        //然后，……
        pageInfo.setList(orderVOList);
        return pageInfo;
    }


    /**
     * 工具方法：把List<Order>拼装成List<OrderVO>
     * @param orderList
     * @return
     */
    private List<OrderVO> orderListToOrderVOList(List<Order> orderList) {
        List<OrderVO> orderVOList = new ArrayList<>();
        for (int i = 0; i < orderList.size(); i++) {
            Order order =  orderList.get(i);
            //调用getOrderVO()方法，把每个Order拼装成OrderVO
            OrderVO orderVO = getOrderVO(order);
            orderVOList.add(orderVO);
        }
        return orderVOList;
    }
}