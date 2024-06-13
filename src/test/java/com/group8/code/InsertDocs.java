package com.group8.code;

import com.group8.code.domain.Servicio;
import com.group8.code.domain.User;
import com.group8.code.domain.Vehicle;
import com.group8.code.repository.ServiceRepository;
import com.group8.code.repository.UserRepository;
import com.group8.code.repository.VehicleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@SpringBootTest
public class InsertDocs {
    @Autowired
    UserRepository userRepository;
    @Autowired
    VehicleRepository vehicleRepository;
    @Autowired
    ServiceRepository serviceRepository;

    @Test
    void insertCustomers(){
        String roleId = "666b20a2218c8e5a55eab269";
        String admin = "6669746d8f7af22a3d1868c1";
        String empl = "666b1fa2218c8e5a55eab266";

        User user1 = User.builder()
                .firstName("Juan")
                .lastName("Pérez")
                .email("juan.perez@example.com")
                .phone("75493444")
                .address("Calle Falsa 123")
                .username("juanpp")
                .password("abc1234")
                .roleId(roleId)
                .build();

        User user2 = User.builder()
                .firstName("María")
                .lastName("García")
                .email("maria.garcia@example.com")
                .phone("7654321")
                .address("Avenida Siempre Viva 456")
                .username("mariag")
                .password("abc1234")
                .roleId(roleId)
                .build();

        User user3 = User.builder()
                .firstName("Carlos")
                .lastName("Rodríguez")
                .email("carlos.rodriguez@example.com")
                .phone("1122334455")
                .address("Calle de la Amargura 789, Ciudad")
                .username("carlosr")
                .password("abc1234")
                .roleId(roleId)
                .build();

        User user4 = User.builder()
                .firstName("Ana")
                .lastName("Martínez")
                .email("ana.martinez@example.com")
                .phone("63445566")
                .address("Boulevard de los Sueños 101, Ciudad")
                .username("anam")
                .password("abc1234")
                .roleId(empl)
                .position("Mantenimiento Técnico")
                .build();

        User user5 = User.builder()
                .firstName("Luis")
                .lastName("Hernández")
                .email("luis.hernandez@example.com")
                .phone("74455667")
                .address("Plaza de la Libertad 202, Ciudad")
                .position("Ventas")
                .username("luish")
                .password("abc1234")
                .roleId(roleId)
                .build();

        User user6 = User.builder()
                .firstName("Elena")
                .lastName("Gómez")
                .email("elena.gomez@example.com")
                .phone("75667788")
                .address("Calle del Sol 303, Ciudad")
                .username("elenag")
                .password("abc1234")
                .roleId(empl)
                .build();

        User user7 = User.builder()
                .firstName("Miguel")
                .lastName("López")
                .email("miguel.lopez@example.com")
                .phone("5566778899")
                .address("Calle de la Luna 414")
                .position("Mantenimiento Técnico")
                .username("miguell")
                .password("password7")
                .roleId(empl)

                .build();

        User user8 = User.builder()
                .firstName("Natalia")
                .lastName("Ramírez")
                .email("natalia.ramirez@example.com")
                .phone("6677900")
                .address("Calle de las Flores 515, ")
                .position("Ingeniera de Mantenimiento")
                .username("nataliaR")
                .password("abc1234")
                .roleId(empl)
                .build();

        User user9 = User.builder()
                .firstName("Jorge")
                .lastName("Mendoza")
                .email("jorge.mendoza@gmail.com")
                .phone("77887011")
                .address("Avenida del Parque 616")
                .position("Supervisor de Mantenimiento")
                .username("jorgeM")
                .password("abc1234")
                .roleId(admin)
                .build();

        User user12 = User.builder()
                .firstName("Pablo")
                .lastName("Ramos")
                .email("pablo.ramos@example.com")
                .phone("62230344")
                .address("Calle del Comercio 333, Ciudad")
                .position("Mantenimiento Técnico")
                .username("pablor")
                .password("abc1234")
                .roleId(empl)
                .build();
        User[] users = {user12};
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


        for (User user : users) {
            //System.out.println(user);
            String encode = passwordEncoder.encode("abc1234");
            user.setPassword(encode);

            userRepository.save(user);
        }

    }

    @Test
    void insertEmployees(){
        String roleId = "66677ae3e9567ad0fac6d028";

        for (int i = 0; i < 4; i++) {

            User user = new User();
            user.setFirstName("Employee" + i);
            user.setLastName("LastName" + i);
            user.setEmail("employee" + i + "@example.com");
            user.setPhone("6123456" + i);
            user.setAddress("Avenida " + i);
            user.setUsername("6123456" + i);
            user.setPassword("password");
            user.setRoleId(roleId);
            userRepository.save(user);
        }
    }

    @Test
    void getCustomers(){
        String roleId = "66678890e9567ad0fac6d029";
        List<User> allByRoleId = userRepository.findAllByRoleId(roleId).orElse(new ArrayList<>());
        List<String> userIds = allByRoleId.stream()
                .map(User::getId)
                .collect(Collectors.toList());
        System.out.println(userIds);
    }

    @Test
    void insertVehicles(){
        String[] ids = {"66678d0c5970455f1e3a06f5", "66678d0d5970455f1e3a06f6", "66678d0d5970455f1e3a06f7", "66678d0e5970455f1e3a06f8"};
        for (int i = 1; i <= 5; i++) {
            Vehicle vehicle = new Vehicle();
            vehicle.setLicensePlate("ABC123" + i);
            vehicle.setBrand("Brand" + i);
            vehicle.setModel("Model" + i);
            vehicle.setYear(2000 + i);
            vehicle.setVin("VIN123456789" + i);
            vehicle.setCustomerId(ids [i % ids.length]);
            vehicleRepository.save(vehicle);
        }
    }

//    @Test
//    void insertServices(){
//        Servicio service1 = Servicio.builder()
//                .name("Cambio de bomba de freno")
//                .description("Reemplazo de la bomba de freno para asegurar el correcto funcionamiento del sistema de frenos.")
//                .build();
//
//        Servicio service2 = Servicio.builder()
//                .name("Cambio de líquido de dirección asistida")
//                .description("Sustitución del líquido de dirección asistida para mantener una dirección suave y efectiva.")
//                .build();
//
//        Servicio service3 = Servicio.builder()
//                .name("Cambio de tapa de radiador")
//                .description("Reemplazo de la tapa del radiador para asegurar un sello adecuado y evitar fugas.")
//                .build();
//
//        Servicio service4 = Servicio.builder()
//                .name("Cambio de bujes de suspensión")
//                .description("Sustitución de los bujes de la suspensión para mejorar la estabilidad y el manejo del vehículo.")
//                .build();
//
//        Servicio service5 = Servicio.builder()
//                .name("Cambio de cables de bujías")
//                .description("Reemplazo de los cables de las bujías para asegurar una correcta conducción de la electricidad al motor.")
//                .build();
//
//        Servicio service6 = Servicio.builder()
//                .name("Cambio de bomba de embrague")
//                .description("Reemplazo de la bomba de embrague para asegurar un funcionamiento suave del sistema de embrague.")
//                .build();
//
//        Servicio service7 = Servicio.builder()
//                .name("Cambio de filtro de combustible")
//                .description("Sustitución del filtro de combustible para mantener el sistema de combustible limpio y eficiente.")
//                .build();
//
//        Servicio service8 = Servicio.builder()
//                .name("Cambio de correa de accesorios")
//                .description("Reemplazo de la correa de accesorios para prevenir fallas en los componentes auxiliares del motor.")
//                .build();
//
//        Servicio service9 = Servicio.builder()
//                .name("Reparación de parabrisas")
//                .description("Reparación de daños en el parabrisas para asegurar una visión clara y mantener la integridad estructural.")
//                .build();
//
//        Servicio service10 = Servicio.builder()
//                .name("Cambio de muelles de suspensión")
//                .description("Sustitución de los muelles de suspensión para mantener una conducción suave y estable.")
//                .build();
//
//        Servicio service11 = Servicio.builder()
//                .name("Cambio de llantas")
//                .description("Reemplazo de las llantas desgastadas para asegurar una tracción y seguridad adecuadas.")
//                .build();
//
//        Servicio service12 = Servicio.builder()
//                .name("Inspección de luces")
//                .description("Verificación y ajuste de todas las luces del vehículo para asegurar un funcionamiento correcto.")
//                .build();
//
//        Servicio service13 = Servicio.builder()
//                .name("Cambio de catalizador")
//                .description("Reemplazo del catalizador para asegurar una correcta conversión de los gases de escape.")
//                .build();
//
//        Servicio service14 = Servicio.builder()
//                .name("Cambio de cilindro maestro de freno")
//                .description("Sustitución del cilindro maestro de freno para mantener el rendimiento del sistema de frenos.")
//                .build();
//
//        Servicio service15 = Servicio.builder()
//                .name("Cambio de depósito de expansión")
//                .description("Reemplazo del depósito de expansión para asegurar una correcta regulación del nivel del refrigerante.")
//                .build();
//
//        Servicio service16 = Servicio.builder()
//                .name("Cambio de palieres")
//                .description("Sustitución de los palieres para mantener la transmisión del par motor a las ruedas.")
//                .build();
//
//        Servicio service17 = Servicio.builder()
//                .name("Cambio de inyectores de combustible")
//                .description("Reemplazo de los inyectores de combustible para asegurar una correcta pulverización del mismo en el motor.")
//                .build();
//
//        Servicio service18 = Servicio.builder()
//                .name("Cambio de termostato")
//                .description("Sustitución del termostato para mantener la temperatura óptima del motor.")
//                .build();
//
//        Servicio service19 = Servicio.builder()
//                .name("Cambio de sensor de oxígeno")
//                .description("Reemplazo del sensor de oxígeno para asegurar una correcta medición de los gases de escape.")
//                .build();
//
//        Servicio service20 = Servicio.builder()
//                .name("Cambio de motor de arranque")
//                .description("Sustitución del motor de arranque para asegurar un arranque eficiente del motor.")
//                .build();
//
//        Servicio service21 = Servicio.builder()
//                .name("Mantenimiento de bujías")
//                .description("Revisión y limpieza de las bujías para asegurar un encendido eficiente del motor.")
//                .build();
//
//        Servicio service22 = Servicio.builder()
//                .name("Cambio de bomba de agua")
//                .description("Reemplazo de la bomba de agua para mantener el sistema de refrigeración del motor en buen estado.")
//                .build();
//
//        Servicio service23 = Servicio.builder()
//                .name("Cambio de fuelles")
//                .description("Sustitución de los fuelles para mantener los componentes de la transmisión y suspensión protegidos.")
//                .build();
//
//        Servicio service24 = Servicio.builder()
//                .name("Cambio de bomba de gasolina")
//                .description("Reemplazo de la bomba de gasolina para asegurar un suministro constante de combustible al motor.")
//                .build();
//
//        Servicio service25 = Servicio.builder()
//                .name("Lavado exterior")
//                .description("Limpieza y detallado del exterior del vehículo para mantener una apariencia impecable.")
//                .build();
//
//        Servicio service26 = Servicio.builder()
//                .name("Cambio de aceite")
//                .description("Reemplazo del aceite del motor para mantenerlo lubricado y funcionando correctamente.")
//                .build();
//
//        Servicio[] services = {
//                service1, service2, service3, service4, service5,
//                service6, service7, service8, service9, service10,
//                service11, service12, service13, service14, service15,
//                service16, service17, service18, service19, service20,
//                service21, service22, service23, service24, service25,
//                service26
//        };
//
//        for (Servicio service : services) {
////            System.out.println(service);
//            serviceRepository.save(service);
//        }
//    }
}
