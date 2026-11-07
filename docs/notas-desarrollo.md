Genere clases Artista, Solista y Banda. Enum EstadoArtista y Excepcion ArtistaDuplicadoException
me falta generar menu
En cuanto a la clase Artista tengo dudas en cuanto al ID, lo genera Base de datos y luego lo tomamos de ahi?
Donde se verifica el duplicado de Artista?
que considera duplicado a un artista?
distingue mayuscula y minusculas?
verifica solo artistas activos? o todos los registros?
que metodo permite consultar si ya existe?
Me falta hacer la interfaz Tourable, clase Album, revisar clase Lanzamiento por las dudas, y hacer el MenuLanzamiento.
Voy a actualizar luego el UML para que se corresponda con los detalles que no coinciden (ENUM Genero)

-------------------
update 27/10: cambiados los archivos a sus carpetas/paquetes y hecha la interfaz Tourable. Actualizado el UML con ENUM e interfaz Identificable.
Falta clase Album, revisar Lanzamiento, y hacer MenuLanzamiento.
(!)Si les parece: Cancion no necesita dao propio. La guardariamos en Lanzamiento dao. 
No existe una cancion "suelta" fuera de un lanzamiento single o album, y si se quiere buscar se hace en el contexto del lanzamiento.
Tendriamos 3 clases con persistencia: Artista, Contrato, y Lanzamiento.
--------------
