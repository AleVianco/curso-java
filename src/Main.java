import dominio.Articulo;
import dominio.Pedido;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static ArrayList<Articulo> listaArticulos = new ArrayList<>();
    static ArrayList<Pedido> listaPedidos = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion = 0;
        do {
            System.out.println("\n--- Menú Principal ---");
            System.out.println("1. Menú Artículos");
            System.out.println("2. Menú Pedidos");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = leerEntero();

            switch (opcion) {
                case 1 -> menuArticulos();
                case 2 -> menuPedidos();
                case 3 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción inválida");
            }
        } while (opcion != 3);
    }

    private static void menuArticulos() {
        int opcion;
        do {
            System.out.println("\n--- Menú Artículos ---");
            System.out.println("1. Crear artículo");
            System.out.println("2. Listar artículos");
            System.out.println("3. Modificar artículo");
            System.out.println("4. Eliminar artículo");
            System.out.println("5. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            opcion = leerEntero();

            switch (opcion) {
                case 1 -> crearArticulo();
                case 2 -> listarArticulos();
                case 3 -> modificarArticulo();
                case 4 -> eliminarArticulo();
                case 5 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción inválida");
            }
        } while (opcion != 5);
    }

    private static void crearArticulo() {
        System.out.println("Creando nuevo artículo.");
        String nombre;
        do {
            System.out.print("Nombre: ");
            nombre = sc.nextLine().trim();
            if (nombre.isEmpty()) {
                System.out.println("El nombre no puede estar vacío.");
            }
        } while (nombre.isEmpty());

        BigDecimal precio;
        do {
            System.out.print("Precio: ");
            precio = leerBigDecimal();
            if (precio.compareTo(BigDecimal.ZERO) < 0) {
                System.out.println("El precio debe ser mayor o igual a cero.");
            }
        } while (precio.compareTo(BigDecimal.ZERO) < 0);

        Articulo nuevo = new Articulo(nombre, precio);
        listaArticulos.add(nuevo);
        System.out.println("Artículo agregado con ID: " + nuevo.getId());
    }

    private static void listarArticulos() {
        if (listaArticulos.isEmpty()) {
            System.out.println("No hay artículos cargados.");
        } else {
            System.out.println("Listado de artículos:");
            for (Articulo art : listaArticulos) {
                System.out.println(art);
            }
        }
    }

    private static void modificarArticulo() {
        if (listaArticulos.isEmpty()) {
            System.out.println("No hay artículos para modificar.");
            return;
        }

        mostrarListaSiDeseaArticulo();

        System.out.print("Ingrese ID del artículo a modificar: ");
        int id = leerEntero();
        Articulo art = buscarArticuloPorId(id);
        if (art == null) {
            System.out.println("No se encontró artículo con ID " + id);
            return;
        }

        System.out.print("Nuevo nombre (actual: " + art.getNombre() + "): ");
        String nuevoNombre = sc.nextLine().trim();
        if (!nuevoNombre.isEmpty()) {
            art.setNombre(nuevoNombre);
        } else {
            System.out.println("Nombre no modificado.");
        }

        System.out.print("Nuevo precio (actual: " + art.getPrecio() + "): ");
        BigDecimal nuevoPrecio = leerBigDecimal();
        if (nuevoPrecio.compareTo(BigDecimal.ZERO) >= 0) {
            art.setPrecio(nuevoPrecio);
        } else {
            System.out.println("Precio no modificado.");
        }

        System.out.println("Artículo actualizado:");
        System.out.println(art);
    }

    private static void eliminarArticulo() {
        if (listaArticulos.isEmpty()) {
            System.out.println("No hay artículos para eliminar.");
            return;
        }

        mostrarListaSiDeseaArticulo();

        System.out.print("Ingrese ID del artículo a eliminar: ");
        int id = leerEntero();
        Articulo art = buscarArticuloPorId(id);
        if (art == null) {
            System.out.println("No se encontró artículo con ID " + id);
            return;
        }

        listaArticulos.remove(art);
        System.out.println("Artículo con ID " + id + " eliminado.");
    }

    private static void menuPedidos() {
        int opcion;
        do {
            System.out.println("\n--- Menú Pedidos ---");
            System.out.println("1. Crear pedido");
            System.out.println("2. Listar pedidos");
            System.out.println("3. Modificar pedido");
            System.out.println("4. Eliminar pedido");
            System.out.println("5. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            opcion = leerEntero();

            switch (opcion) {
                case 1 -> crearPedido();
                case 2 -> listarPedidos();
                case 3 -> modificarPedido();
                case 4 -> eliminarPedido();
                case 5 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción inválida");
            }
        } while (opcion != 5);
    }

    private static void crearPedido() {
        if (listaArticulos.isEmpty()) {
            System.out.println("No se pueden crear pedidos sin artículos.");
            return;
        }

        Pedido nuevoPedido = new Pedido();

        System.out.println("Creando nuevo pedido.");

        boolean continuar = true;
        while (continuar) {
            System.out.println("Seleccione artículo para agregar al pedido:");
            listarArticulos();
            System.out.print("Ingrese ID del artículo: ");
            int id = leerEntero();
            Articulo art = buscarArticuloPorId(id);
            if (art == null) {
                System.out.println("Artículo no encontrado.");
                continue;
            }

            System.out.print("Ingrese cantidad: ");
            int cantidad = leerEntero();
            if (cantidad <= 0) {
                System.out.println("Cantidad inválida.");
                continue;
            }

            nuevoPedido.agregarArticulo(art, cantidad);

            System.out.println("¿Desea agregar otro artículo? (1=Sí / 2=No): ");
            int op = leerEntero();
            if (op != 1) {
                continuar = false;
            }
        }
        listaPedidos.add(nuevoPedido);
        System.out.println("Pedido creado con ID: " + nuevoPedido.getId());
    }

    private static void listarPedidos() {
        if (listaPedidos.isEmpty()) {
            System.out.println("No hay pedidos cargados.");
        } else {
            System.out.println("Listado de pedidos:");
            for (Pedido ped : listaPedidos) {
                System.out.println(ped);
            }
        }
    }

    private static void modificarPedido() {
        if (listaPedidos.isEmpty()) {
            System.out.println("No hay pedidos para modificar.");
            return;
        }

        mostrarListaSiDeseaPedido();

        System.out.print("Ingrese ID del pedido a modificar: ");
        int id = leerEntero();
        Pedido pedido = buscarPedidoPorId(id);
        if (pedido == null) {
            System.out.println("No se encontró pedido con ID " + id);
            return;
        }

        boolean salir = false;
        while (!salir) {
            System.out.println("\nModificando pedido ID: " + pedido.getId());
            System.out.println("1. Agregar artículo");
            System.out.println("2. Quitar artículo");
            System.out.println("3. Ver pedido");
            System.out.println("4. Salir de modificación");
            System.out.print("Seleccione una opción: ");
            int opcion = leerEntero();

            switch (opcion) {
                case 1 -> {
                    listarArticulos();
                    System.out.print("Ingrese ID del artículo a agregar: ");
                    int idArt = leerEntero();
                    Articulo art = buscarArticuloPorId(idArt);
                    if (art == null) {
                        System.out.println("Artículo no encontrado.");
                        break;
                    }
                    System.out.print("Ingrese cantidad: ");
                    int cant = leerEntero();
                    if (cant <= 0) {
                        System.out.println("Cantidad inválida.");
                        break;
                    }
                    pedido.agregarArticulo(art, cant);
                    System.out.println("Artículo agregado.");
                }
                case 2 -> {
                    if (pedido.getArticulosCantidad().isEmpty()) {
                        System.out.println("El pedido no tiene artículos para eliminar.");
                        break;
                    }
                    System.out.println("Artículos en el pedido:");
                    pedido.mostrarArticulosConCantidad();
                    System.out.print("Ingrese ID del artículo a quitar: ");
                    int idArtQuitar = leerEntero();
                    if (pedido.quitarArticulo(idArtQuitar)) {
                        System.out.println("Artículo quitado del pedido.");
                    } else {
                        System.out.println("Artículo no encontrado en el pedido.");
                    }
                }
                case 3 -> pedido.mostrarPedidoCompleto();
                case 4 -> salir = true;
                default -> System.out.println("Opción inválida");
            }
        }
    }

    private static void eliminarPedido() {
        if (listaPedidos.isEmpty()) {
            System.out.println("No hay pedidos para eliminar.");
            return;
        }

        mostrarListaSiDeseaPedido();

        System.out.print("Ingrese ID del pedido ");
        int id = leerEntero();
        Pedido pedido = buscarPedidoPorId(id);
        if (pedido == null) {
            System.out.println("No se encontró pedido con ID " + id);
            return;
        }

        listaPedidos.remove(pedido);
        System.out.println("Pedido con ID " + id + " eliminado.");
    }

// Métodos auxiliares

    private static void mostrarListaSiDeseaArticulo() {
        System.out.println("¿Desea ver la lista de artículos antes de continuar?");
        System.out.println("1. Sí");
        System.out.println("2. No");
        int opcionLista = leerEntero();
        if (opcionLista == 1) {
            listarArticulos();
        }
    }

    private static void mostrarListaSiDeseaPedido() {
        System.out.println("¿Desea ver la lista de pedidos antes de continuar?");
        System.out.println("1. Sí");
        System.out.println("2. No");
        int opcionLista = leerEntero();
        if (opcionLista == 1) {
            listarPedidos();
        }
    }

    private static Articulo buscarArticuloPorId(int id) {
        for (Articulo art : listaArticulos) {
            if (art.getId() == id) {
                return art;
            }
        }
        return null;
    }

    private static Pedido buscarPedidoPorId(int id) {
        for (Pedido ped : listaPedidos) {
            if (ped.getId() == id) {
                return ped;
            }
        }
        return null;
    }

    private static int leerEntero() {
        int numero;
        while (true) {
            try {
                String input = sc.nextLine();
                numero = Integer.parseInt(input);
                if (numero < 0) {
                    System.out.print("Ingrese un número entero positivo: ");
                } else {
                    return numero;
                }
            } catch (NumberFormatException e) {
                System.out.print("Entrada inválida. Ingrese un número entero: ");
            }
        }
    }

    private static BigDecimal leerBigDecimal() {
        while (true) {
            try {
                String input = sc.nextLine().replace(".", "").replace(",", ".");
                BigDecimal valor = new BigDecimal(input);
                return valor.setScale(2, BigDecimal.ROUND_HALF_UP);
            } catch (NumberFormatException e) {
                System.out.print("Entrada inválida. Ingrese un número decimal válido: ");
            }
        }
    }
}

