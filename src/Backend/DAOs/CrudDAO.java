package Backend.DAOs;


import java.util.List;

public interface CrudDAO<T> {
    List<T> obtenerTodos();
    T obtenerPorID(int id);
    void insertar(T entidad);
    void actualizar(int id, T entidad);
    void eliminar(int id);
}
