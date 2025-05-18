package dominio;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.concurrent.atomic.AtomicInteger;

public class Articulo {
    private static final AtomicInteger contadorId = new AtomicInteger(0);

    private final int id;
    private String nombre;
    private BigDecimal precio;

    public Articulo(String nombre, BigDecimal precio) {
        this.id = contadorId.incrementAndGet();
        setNombre(nombre);
        setPrecio(precio);
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        this.nombre = nombre.trim();
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        if (precio == null || precio.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El precio debe ser un número positivo o cero.");
        }
        this.precio = precio.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public String toString() {
        // Formato de precio: miles con punto, decimales con coma
        DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
        simbolos.setDecimalSeparator(',');
        simbolos.setGroupingSeparator('.');
        DecimalFormat df = new DecimalFormat("#,##0.00", simbolos);
        return "ID: " + id + ", Nombre: " + nombre + ", Precio: " + df.format(precio);
    }
}