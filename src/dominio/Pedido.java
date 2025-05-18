package dominio;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Pedido {
    private static final AtomicInteger contadorId = new AtomicInteger(0);

    private final int id;
    private Map<Articulo, Integer> articulosCantidad;

    public Pedido() {
        this.id = contadorId.incrementAndGet();
        this.articulosCantidad = new HashMap<>();
    }

    public int getId() {
        return id;
    }

    public Map<Articulo, Integer> getArticulosCantidad() {
        return articulosCantidad;
    }

    public void agregarArticulo(Articulo articulo, int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser positiva.");
        }
        articulosCantidad.put(articulo, articulosCantidad.getOrDefault(articulo, 0) + cantidad);
    }

    public boolean quitarArticulo(int idArticulo) {
        Articulo aEliminar = null;
        for (Articulo a : articulosCantidad.keySet()) {
            if (a.getId() == idArticulo) {
                aEliminar = a;
                break;
            }
        }
        if (aEliminar != null) {
            articulosCantidad.remove(aEliminar);
            return true;
        }
        return false;
    }

    public BigDecimal getTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (Map.Entry<Articulo, Integer> entry : articulosCantidad.entrySet()) {
            BigDecimal subtotal = entry.getKey().getPrecio().multiply(BigDecimal.valueOf(entry.getValue()));
            total = total.add(subtotal);
        }
        return total.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public void mostrarArticulosConCantidad() {
        for (Map.Entry<Articulo, Integer> entry : articulosCantidad.entrySet()) {
            Articulo art = entry.getKey();
            int cantidad = entry.getValue();
            System.out.println("ID: " + art.getId() + ", Nombre: " + art.getNombre() + ", Cantidad: " + cantidad + ", Precio unitario: " + formatearPrecio(art.getPrecio()));
        }
    }

    public void mostrarPedidoCompleto() {
        System.out.println("Pedido ID: " + id);
        mostrarArticulosConCantidad();
        System.out.println("Total: " + formatearPrecio(getTotal()));
    }

    private String formatearPrecio(BigDecimal precio) {
        DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
        simbolos.setDecimalSeparator(',');
        simbolos.setGroupingSeparator('.');
        DecimalFormat df = new DecimalFormat("#,##0.00", simbolos);
        return df.format(precio);
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Total: " + formatearPrecio(getTotal()) + ", Artículos: " + articulosCantidad.size();
    }
}

