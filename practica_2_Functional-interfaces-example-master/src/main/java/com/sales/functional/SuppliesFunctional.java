package com.sales.functional;


import com.sales.functional.database.Database;
import com.sales.functional.entities.Product;
import com.sales.functional.entities.Sale;

import java.time.ZoneId;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;


import java.util.stream.Collectors;





public class SuppliesFunctional {
    static ArrayList<Sale> sales = Database.loadDatabase();
    public static void main(String[] args) {
        loadMenu();

    }


    /** 1. Obtenga todas las ventas(Sale) que tengan como método de compra(Purchase method) 'Online'

        2. Obtenga todas las ventas(Sale) que tengan como ubicación New York y filtre también validando si las ventas fueron con cupón o sin cupón

        3. Obtenga la cantidad de ventas en las que los clientes usaron cupón

        4. Obtenga todas las ventas que fueron realizadas un año específico 'YYYY'

        5. Obtenga el número de ventas en donde el indicador de satisfacción es menor a 4.

        6. Calcule el monto total que pagó el cliente en cada venta.

        7. Obtenga todas las ventas en las que el comprador es una mujer y fue comprado en la tienda ('in store')

        8. Obtenga el número de productos comprados por todos los clientes segmentándolos por etiquetas(tags)

        9. Obtenga cuantos hombres usaron cupón y cuantas mujeres usaron cupón;

        10. Obtenga la venta con la compra más costosa y la venta con la compra más barata
     */

    public static void menu(){
        System.out.println("Supplies sales");
        System.out.println("1. Compras en linea");
        System.out.println("2. Compras realizadas en New York con o sin cupón");
        System.out.println("3. el numero de ventas en donde se usaron cupones y en el numero en las que no");
        System.out.println("4. Ventas realizadas en el año YYYY");
        System.out.println("5. Ventas en donde el indicador de satisfacción es menor a N");
        //TO DO:
        System.out.println("6. Monto total pagado en cada venta");
        System.out.println("7. Ventas en donde compró una mujer en la tienda(in store)");
        System.out.println("8. Agrupación de productos por etiquetas(tags)");
        System.out.println("9. Cuantos hombres y mujeres usaron cupón");
        System.out.println("10. Venta con mayor costo y menor costo");

    }

    public static void loadMenu(){
        Scanner sc = new Scanner(System.in);

        String op = "";
        while (op!="0") {
            menu();
            System.out.print("Type option: ");
            op = sc.nextLine();


            switch (op) {
                case "1":
                    getOnlinePurchases();
                    break;
                case "2":
                    System.out.print("¿quiere filtrar las ventas que usaron cupón? Y/N: ");
                    getNySales(sc.nextLine());
                    break;
                case "3":
                    couponUsage();
                    break;
                case "4":
                    System.out.print("Cual es el año por el que quiere filtrar: ");
                    salesByYear(sc.nextLine());
                    break;
                case "5":
                    System.out.print("Cual es el numero de satisfacción por que quiere filtrar (1-5): ");
                    salesBySatisfaction(sc.nextLine());
                    break;
                case "6":
                    System.out.println("Monto total pagado en cada venta");
                    getTotalAmount();
                    break;
                case "7":
                    System.out.println("Ventas en donde compró una mujer en la tienda(in store)");
                    getSalesByWomen();
                    break;
                case "8":
                    System.out.println("Agrupación de productos por etiquetas(tags)");
                    groupByTags();
                    break;
                case "9":
                    System.out.println("hombres y mujeres que usaron cupón");
                    getMenAndWomenCoupons();
                    break;
                case "10":
                    System.out.println("Venta con mayor costo y menor costo");
                    getMostAndLeastExpensive();
                    break;
                case "0":
                    break;
                default:
                    System.out.println("ERROR en el input, este metodo no ha sido creado. Intente de nuevo");
            }
        }

    }

    // crear un metodo static que permita obtener el monto total pagado en cada venta

    public static void groupByTags(){
        Map<String, Set<String>> result;
        //result = sales.stream().collect(Collectors.groupingBy(Sale::getTags, Collectors.mapping(Sale::getTags, Collectors.toSet())));

        result = sales.stream()
                .flatMap(sale -> sale.getItems().stream())
                .collect(Collectors.groupingBy(Product::getName, Collectors.flatMapping(product -> product.getTags().stream(), Collectors.toSet()) ));

        System.out.println(result);
    }

    public static void getMostAndLeastExpensive(){
        Sale salemm;
        Optional<Sale> max = sales.stream().max(Comparator.comparing(sale -> sale.getItems().stream().mapToDouble(Product::getPrice).sum()));
        Optional<Sale> min = sales.stream().min(Comparator.comparing(sale -> sale.getItems().stream().mapToDouble(Product::getPrice).sum()));

        if (max.isPresent()) {
            salemm = max.get();
            System.out.println("Venta más costosa: " + max.get().getItems().stream().mapToDouble(Product::getPrice).sum());
        }
        if (min.isPresent()) {
            salemm = max.get();
            System.out.println("Venta más barata: " + min.get().getItems().stream().mapToDouble(Product::getPrice).sum());
        }
    }
    public static void getMenAndWomenCoupons(){
        Predicate<Sale> salesByMen = sale -> sale.getCustomer().getGender().equals("m") && sale.getCouponUsed().equals(true);
        Long menCoupons = sales.stream().filter(salesByMen).count();

        Predicate<Sale> salesByWomen = sale -> sale.getCustomer().getGender().equals("f") && sale.getCouponUsed().equals(true);
        Long womenCoupons = sales.stream().filter(salesByWomen).count();

        System.out.println("Hombres que usaron cupón: " + menCoupons);
        System.out.println("Mujeres que usaron cupón: " + womenCoupons);

    }
    public static void getTotalAmount(){
        // crear un consumer que permita imprimir el monto total de cada venta
        Consumer<Sale> printTotalAmount = sale -> System.out.println("Cliente de la Venta : " + sale.getCustomer().getEmail()
                + " Total Venta " + sale.getItems().stream().mapToDouble(Product::getPrice).sum());
        //Consumer crea una nueva collección, en este caso de strings

        sales.stream().forEach(printTotalAmount);
    }

    public static void getSalesByWomen(){
        Predicate<Sale> salesByWomen = sale -> sale.getCustomer().getGender().equals("f") && sale.getPurchasedMethod().equals("in store");
        ArrayList<Sale> result = sales.stream().filter(salesByWomen).collect(Collectors.toCollection(ArrayList::new));
        result.forEach(System.out::println);
    }
     public static void getOnlinePurchases(){
        Predicate<Sale> onlinePurchased = sale -> sale.getPurchasedMethod().equals("Online");
        ArrayList<Sale> result = sales.stream().filter(onlinePurchased).collect(Collectors.toCollection(ArrayList::new));
        result.forEach(System.out::println);

    }

    public static void getNySales(String inCoupon){
        Predicate<Sale> couponUsage = sale -> sale.getCouponUsed().equals(inCoupon.equalsIgnoreCase("Y")) && sale.getLocation().equals("New York");
        ArrayList<Sale> result = sales.stream().filter(couponUsage).collect(Collectors.toCollection(ArrayList::new));
        result.forEach(System.out::println);

    }

    public static void couponUsage(){
        Predicate<Sale> couponUsage = Sale::getCouponUsed;
        Predicate<Sale> couponNoUsage = sale -> !sale.getCouponUsed();
        Map<String,Long> usage  = new HashMap<>(){{
            put("Usage",sales.stream().filter(couponUsage).count());
            put("Not usage",sales.stream().filter(couponNoUsage).count());
        }};

        usage.forEach((key,value)-> System.out.println(key+": "+value));

    }

    public static void salesByYear(String inYear){
        Function<Sale,String> getYear = sale -> String.valueOf(sale.getSaleDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear());
        ArrayList<Sale> salesByYYYY = sales.stream().filter(sale -> getYear.apply(sale).equals(inYear)).collect(Collectors.toCollection(ArrayList::new));
        salesByYYYY.forEach(System.out::println);
    }

    public static void salesBySatisfaction(String inSatis){
        Consumer<String> satisfaction = satis -> sales.stream().filter(sale -> sale.getCustomer().getSatisfaction().toString().equals(satis)).collect(Collectors.toCollection(ArrayList::new)).forEach(System.out::println);
        satisfaction.accept(inSatis);
    }
}
