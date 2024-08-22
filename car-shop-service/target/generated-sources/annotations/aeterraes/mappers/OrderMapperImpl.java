package aeterraes.mappers;

import aeterraes.dataaccess.entities.Order;
import aeterraes.dataaccess.models.OrderStatus;
import aeterraes.dtos.OrderDTO;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-18T20:45:23+0300",
    comments = "version: 1.6.0, compiler: javac, environment: Java 19.0.1 (Oracle Corporation)"
)
public class OrderMapperImpl implements OrderMapper {

    @Override
    public OrderDTO orderToOrderDTO(Order order) {
        if ( order == null ) {
            return null;
        }

        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setOrderId( order.getOrderId() );
        orderDTO.setCustomerId( order.getCustomerId() );
        orderDTO.setCarId( order.getCarId() );
        orderDTO.setTotalPrice( order.getTotalPrice() );
        if ( order.getStatus() != null ) {
            orderDTO.setStatus( order.getStatus().name() );
        }
        orderDTO.setOrderDate( order.getOrderDate() );

        return orderDTO;
    }

    @Override
    public Order orderDTOToOrder(OrderDTO orderDTO) {
        if ( orderDTO == null ) {
            return null;
        }

        Order order = new Order();

        order.setOrderId( orderDTO.getOrderId() );
        order.setCustomerId( orderDTO.getCustomerId() );
        order.setCarId( orderDTO.getCarId() );
        order.setTotalPrice( orderDTO.getTotalPrice() );
        if ( orderDTO.getStatus() != null ) {
            order.setStatus( Enum.valueOf( OrderStatus.class, orderDTO.getStatus() ) );
        }
        order.setOrderDate( orderDTO.getOrderDate() );

        return order;
    }
}
