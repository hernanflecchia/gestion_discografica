package interfaces;

/**
 * Lo implementan las clases que tienen un id (Artista, Contrato, Lanzamiento).
 * Permite que las clases genéricas Gestor y Repositorio trabajen con
 * cualquiera de ellas usando el id.
 */
public interface Identificable {
    int getId();
    void setId(int id);
}
