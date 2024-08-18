package aeterraes.mappers;

import aeterraes.dataaccess.entities.Car;
import aeterraes.dtos.CarDTO;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-18T20:45:23+0300",
    comments = "version: 1.6.0, compiler: javac, environment: Java 19.0.1 (Oracle Corporation)"
)
public class CarMapperImpl implements CarMapper {

    @Override
    public CarDTO carToCarDTO(Car car) {
        if ( car == null ) {
            return null;
        }

        CarDTO carDTO = new CarDTO();

        carDTO.setCarId( car.getCarId() );
        carDTO.setMake( car.getMake() );
        carDTO.setModel( car.getModel() );
        carDTO.setYear( car.getYear() );
        carDTO.setMileage( car.getMileage() );
        carDTO.setColor( car.getColor() );
        carDTO.setEngine( car.getEngine() );
        carDTO.setHorsepower( car.getHorsepower() );
        carDTO.setAcceleration( car.getAcceleration() );
        carDTO.setSuspension( car.getSuspension() );
        carDTO.setGear( car.getGear() );
        carDTO.setDriveTrain( car.getDriveTrain() );
        carDTO.setPrice( car.getPrice() );
        carDTO.setAvailability( car.isAvailability() );

        return carDTO;
    }

    @Override
    public Car carDTOToCar(CarDTO carDto) {
        if ( carDto == null ) {
            return null;
        }

        Car car = new Car();

        car.setCarId( carDto.getCarId() );
        car.setMake( carDto.getMake() );
        car.setModel( carDto.getModel() );
        car.setYear( carDto.getYear() );
        car.setMileage( carDto.getMileage() );
        car.setColor( carDto.getColor() );
        car.setEngine( carDto.getEngine() );
        car.setHorsepower( carDto.getHorsepower() );
        car.setAcceleration( carDto.getAcceleration() );
        car.setSuspension( carDto.getSuspension() );
        car.setGear( carDto.getGear() );
        car.setDriveTrain( carDto.getDriveTrain() );
        car.setPrice( carDto.getPrice() );
        car.setAvailability( carDto.isAvailability() );

        return car;
    }
}
