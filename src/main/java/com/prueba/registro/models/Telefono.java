package com.prueba.registro.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="telefonos")
public class Telefono {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String numero;

    @JsonProperty("codigoCiudad")
    private String codigo;

    @JsonProperty("codigoPais")
    private String pais;

    @JsonIgnore
    @ManyToOne
    private Usuario usuario;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigoCiudad) {
        this.codigo = codigoCiudad;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String codigoPais) {
        this.pais = codigoPais;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
