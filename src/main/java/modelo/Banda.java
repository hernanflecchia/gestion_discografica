package modelo;
import enums.EstadoArtista;

import java.util.ArrayList;
import java.util.List;

public class Banda extends  Artista{

    private List<String> integrantes;

    //constructores
    public Banda(String nombre, String pais, List<String> integrantes) {
        super(nombre, pais);
        setIntegrantes(integrantes);
    }

    public Banda(int id, String nombre, String pais, EstadoArtista estado, List<String> integrantes) {
        super(id, nombre, pais, estado);
        setIntegrantes(integrantes);
    }

    //getter
    public List<String> getIntegrantes() {
        return integrantes;
    }

    //setter con validacion


    public void setIntegrantes(List<String> integrantes) {
        if(integrantes == null || integrantes.isEmpty()){
            throw new IllegalArgumentException(
                    "La banda debe tener al menos un integrante."
            );
        }

        List<String> integrantesValidos = new ArrayList<>();//listado auxiliar

        for(String integrante : integrantes){
            if(integrante == null || integrante.trim().isEmpty()){
                throw new IllegalArgumentException(
                        "El nombre de cada integrante no puede estar vacio."
                );
            }
            integrantesValidos.add(integrante);
        }

        this.integrantes = integrantesValidos;
    }

    //implementacion metodos abstracto
    @Override
    public String descripcion() {
        return "Tipo de Artista: Banda";
    }

    //tostring

    @Override
    public String toString() {
        return "Banda: " +
                "\n Integrantes: " + integrantes +
                "| " + super.toString();
    }

    @Override
    public void setId(int id) {

    }
}

//OBSERVACIONES

//aca mantuve misma logica de clase abstracta, no generando desde el constructor, sino tomarlo desde el set
//despues de haber realizado las validaciones y el manejo de excepcion.
//tuve muchas dudas al generar la validacion del setter
//NO ENTIENDO MUCHO COMO MANEJA LA PERSISTENCIA DE DATOS CON UN LISTADO
