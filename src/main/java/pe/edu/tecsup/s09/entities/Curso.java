package pe.edu.tecsup.s09.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "cursos")
public class Curso extends Auditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String codigo;

    @Column(length = 100, nullable = false)
    private String nombre;

    private Integer creditos;

    private Boolean estado = true;

    public Curso() {}

    public Curso(Long id, String codigo, String nombre, Integer creditos, Boolean estado) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.creditos = creditos;
        this.estado = estado;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Integer getCreditos() { return creditos; }
    public void setCreditos(Integer creditos) { this.creditos = creditos; }
    public Boolean getEstado() { return estado; }
    public void setEstado(Boolean estado) { this.estado = estado; }
}