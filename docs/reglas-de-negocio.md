# Gestión Discográfica: enunciado y reglas de negocio

> Documento guía del grupo. Describe **qué tiene que hacer** el sistema y **qué reglas tiene que respetar**.
> Cuando una regla cambie, primero se actualiza acá y después se cambia el código.
> Todas las reglas que estaban **(a confirmar)** ya se decidieron: ver la sección 6.

---

## 1. Enunciado

Un sello discográfico necesita un sistema de consola para administrar a sus **artistas**, los **contratos** que firma con ellos, los **lanzamientos** (singles y álbumes) que publica y las **giras** de esos lanzamientos. Además, el sistema tiene que calcular las **regalías** que le corresponden a cada artista.

Los artistas pueden ser **solistas** o **bandas**. De todos se registra el nombre artístico, el país de origen y el estado (activo o inactivo). De un solista también se registra su nombre real, y de una banda, la lista de sus integrantes.

Para trabajar con el sello, un artista tiene que firmar un **contrato**, que tiene una fecha de inicio, una fecha de fin y un porcentaje de regalías. El contrato puede estar vigente, vencido o rescindido.

Un artista con contrato vigente puede publicar **lanzamientos**. Un lanzamiento tiene título, fecha, precio, género musical y la cantidad de unidades vendidas. Hay dos tipos:

- **Single:** tiene una sola canción.
- **Álbum:** tiene varias canciones y puede salir de **gira** (tour), con shows agendados en distintas fechas y ciudades.

Cada canción tiene un título y una duración en segundos, y solo existe dentro de un lanzamiento.

---

## 2. Glosario

| Término | Significado |
|---|---|
| **Artista** | Solista o banda que trabaja con el sello. |
| **Baja de artista** | Pasar al artista a `INACTIVO`. No se borra de la base. |
| **Contrato vigente** | Contrato no rescindido cuya fecha de hoy está entre `fechaInicio` y `fechaFin`. Un contrato que todavía no empezó **no** está vigente. |
| **Lanzamiento** | Single o álbum publicado por un artista. |
| **Ingresos de un lanzamiento** | Plata que generó: ventas y, en los álbumes, shows. |
| **Regalías** | Parte de los ingresos que le corresponde al artista según el porcentaje de su contrato. |
| **Tour** | Conjunto de shows (fecha + ciudad) de un álbum. |

---

## 3. Reglas de negocio

### 3.1 Artistas

| ID | Regla | Dónde se controla |
|---|---|---|
| RN-A01 | El nombre artístico y el país son obligatorios (no pueden estar vacíos ni ser solo espacios). | `Artista.setNombre / setPais` |
| RN-A02 | Un **solista** tiene que tener nombre real. | `Solista.setNombreReal` |
| RN-A03 | Una **banda** tiene que tener al menos 1 integrante, y ningún nombre de integrante puede estar vacío. | `Banda.setIntegrantes` |
| RN-A04 | El nombre artístico es **único**: no puede haber dos artistas con el mismo nombre, sin importar mayúsculas y minúsculas. Se controla contra **todos** los artistas, activos e inactivos. Si se repite, se lanza `ArtistaDuplicadoException`. | `GestorArtista.validar` + `UNIQUE` en la BD |
| RN-A05 | Todo artista nuevo se crea en estado `ACTIVO`. | Constructor de `Artista` |
| RN-A06 | El id lo genera la base de datos y se asigna **una sola vez**. | `Artista.asignarId` |
| RN-A07 | La baja es **lógica**: el artista pasa a `INACTIVO` y conserva sus contratos y lanzamientos (son historial). Antes de darlo de baja, el menú pide confirmación. | `ArtistaDao.eliminar` + `MenuArtistas.darDeBaja` |
| RN-A08 | Un artista `INACTIVO` no puede firmar contratos ni publicar lanzamientos. | `Sello.verificarActivo` (lo usan `firmarContrato` y `verificarPuedePublicar`) |
| RN-A09 | El tipo de artista (solista/banda) no se puede cambiar después del alta. | No hay setter de tipo |

### 3.2 Contratos

| ID | Regla | Dónde se controla |
|---|---|---|
| RN-C01 | Todo contrato pertenece a un artista existente. | `Contrato.setArtista` + FK en la BD |
| RN-C02 | La fecha de fin tiene que ser **posterior** a la fecha de inicio (no pueden ser iguales). | `Contrato.setFechaInicio / setFechaFin` + `CHECK` en la BD |
| RN-C03 | El porcentaje de regalías tiene que ser mayor a 0 y menor o igual a 100. | `Contrato.setPorcentajeRegalias` + `CHECK` en la BD |
| RN-C04 | Solo un artista `ACTIVO` puede firmar un contrato. | `Sello.firmarContrato` |
| RN-C05 | Un artista no puede tener **dos contratos que se superpongan** en fechas. Cuenta cualquier contrato que no esté rescindido: vigente, futuro o vencido. | `GestorContrato.validar` |
| RN-C06 | El contrato nace `VIGENTE`. | Constructor de `Contrato` |
| RN-C07 | Un contrato `VIGENTE` pasa a `VENCIDO` cuando la fecha de hoy supera `fechaFin`. El estado se recalcula solo cada vez que se consulta. | `Contrato.actualizarEstado` (lo llaman `getEstado` y `estaVigente`) |
| RN-C08 | Un contrato se puede **rescindir** solo si está `VIGENTE`. Si está vencido se lanza `ContratoVencidoException`; si ya estaba rescindido, `IllegalStateException`. La rescisión es definitiva. | `Contrato.rescindir` |
| RN-C09 | `estaVigente()` devuelve `true` solo si el estado no es `RESCINDIDO` y hoy está entre `fechaInicio` y `fechaFin` (inclusive). | `Contrato.estaVigente` |

### 3.3 Lanzamientos y canciones

| ID | Regla | Dónde se controla |
|---|---|---|
| RN-L01 | Título, fecha, género y artista son obligatorios. | `Lanzamiento` (setters) |
| RN-L02 | El precio no puede ser negativo. | `Lanzamiento.setPrecio` + `CHECK` en la BD |
| RN-L03 | Toda canción tiene título y una duración **mayor a 0** segundos. | `Cancion` + `CHECK` en la BD |
| RN-L04 | Un **single** tiene exactamente **1** canción, de hasta **10 minutos** (600 s). | `Single.validar` |
| RN-L05 | Un **álbum** tiene al menos **4** canciones. | `Album.validar` |
| RN-L06 | Si un lanzamiento no cumple las reglas de su tipo, se lanza `LanzamientoInvalidoException`. | `validar()` de cada subclase, llamado desde `GestorLanzamiento.validar` |
| RN-L07 | Para **publicar** un lanzamiento, su artista tiene que estar activo y tener un **contrato vigente**. Si no lo tiene, se lanza `SinContratoVigenteException`. El menú lo verifica apenas se elige el artista, antes de pedir el resto de los datos. | `Sello.verificarPuedePublicar` (lo llaman `publicarLanzamiento` y `MenuLanzamientos`) |
| RN-L08 | Un lanzamiento nuevo arranca con 0 unidades vendidas. | Constructor de `Lanzamiento` |
| RN-L09 | Para registrar una venta, la cantidad tiene que ser mayor a 0, y se **suma** a las unidades vendidas. | `Lanzamiento.registrarVenta` |
| RN-L10 | Las canciones no existen fuera de su lanzamiento (composición): si se elimina el lanzamiento, se eliminan sus canciones. | `ON DELETE CASCADE` en la BD |
| RN-L11 | El tipo de lanzamiento (single/álbum) no se puede cambiar. | No hay setter de tipo |

### 3.4 Tours

| ID | Regla | Dónde se controla |
|---|---|---|
| RN-T01 | Solo los **álbumes** pueden salir de gira (implementan `Tourable`). El menú lo verifica apenas se ingresa el id, antes de pedir fecha y ciudad. | `Album implements Tourable` + `Sello.verificarEsAlbum` |
| RN-T02 | Para agendar un show, la fecha y la ciudad son obligatorias. | `Album.agendarTour` |
| RN-T03 | No puede haber dos shows del mismo álbum en la misma fecha. | `Album.agendarTour` |
| RN-T04 | Solo se puede cancelar un show que exista. Si el álbum no tiene shows, el menú avisa antes de pedir la fecha. | `Album.cancelarTour` + `Sello.verificarTieneShows` |
| RN-T05 | Los shows se listan ordenados por fecha. | `TreeMap` en `Album` |
| RN-T06 | Los tours se manejan **en memoria** con `Repositorio<Album>` y no se guardan en la base. Al cerrar el programa, los shows se pierden. | `Sello.albumesEnGira` |

### 3.5 Ingresos y regalías

| ID | Regla | Dónde se controla |
|---|---|---|
| RN-R01 | Ingresos de un single = `unidadesVendidas × precio`. | `Single.calcularIngresos` |
| RN-R02 | Ingresos de un álbum = `unidadesVendidas × precio + cantidadDeShows × 1000`. | `Album.calcularIngresos` |
| RN-R03 | Regalías de un artista = suma de los ingresos de **todos sus lanzamientos** × `porcentajeRegalias / 100` de su **contrato vigente**. Para los álbumes de gira se usa la copia en memoria, que tiene los shows. | `Sello.calcularRegalias` |
| RN-R04 | Si el artista no tiene contrato vigente, no se calculan regalías: se informa con `SinContratoVigenteException`. | `Sello.calcularRegalias` |
| RN-R05 | Las ventas de un álbum de gira se registran sobre la copia en memoria y se guardan en la base, así las dos quedan iguales. | `Sello.registrarVenta` |

### 3.6 Generales

| ID | Regla | Dónde se controla |
|---|---|---|
| RN-G01 | Si se busca, modifica o da de baja un id que no existe, se lanza `EntidadNoEncontradaException` con el nombre de la entidad y el id. | Los tres DAOs |
| RN-G02 | Las operaciones que guardan varias filas (artista + integrantes, lanzamiento + canciones) usan una transacción: si algo falla, se hace rollback y no queda nada a medias. | `agregar` y `modificar` de los DAOs |

---

## 4. Funcionalidades (casos de uso)

El sistema arranca en un **menú principal** con las opciones Artistas, Lanzamientos, Contratos, Tours y Salir. Cada submenú vuelve al principal y, después de cada operación, pausa para que se pueda leer el resultado.

Toda la entrada por teclado se valida: si el usuario escribe letras donde va un número, deja un campo vacío o pone una fecha con un formato inválido, el sistema le vuelve a pedir el dato y **no se cierra**. Los errores de negocio se muestran como mensajes claros. Cuando una operación depende de una condición (contrato vigente, que sea un álbum, que tenga shows), el sistema la verifica **antes** de pedir el resto de los datos.

### 4.1 Artistas

| # | Funcionalidad | Reglas | Estado |
|---|---|---|---|
| F-A1 | Alta de solista (nombre, país, nombre real) | A01, A02, A04, A05 | ✅ |
| F-A2 | Alta de banda (nombre, país, integrantes) | A01, A03, A04, A05 | ✅ |
| F-A3 | Listar artistas (ordenados por nombre) | — | ✅ |
| F-A4 | Buscar artista por id | G01 | ✅ |
| F-A5 | Dar de baja (baja lógica, con confirmación) | A07 | ✅ |

### 4.2 Contratos

| # | Funcionalidad | Reglas | Estado |
|---|---|---|---|
| F-C1 | Firmar contrato (artista, fechas, porcentaje) | C01–C06, A08 | ✅ |
| F-C2 | Listar contratos | — | ✅ |
| F-C3 | Actualizar estados (marcar como vencidos los que ya pasaron) | C07 | ✅ Automático: el estado se recalcula al consultarlo, no hace falta una opción de menú |
| F-C4 | Rescindir contrato | C08 | ✅ |

### 4.3 Lanzamientos

| # | Funcionalidad | Reglas | Estado |
|---|---|---|---|
| F-L1 | Publicar single (datos + 1 canción) | L01–L04, L06, L07 | ✅ |
| F-L2 | Publicar álbum (datos + canciones) | L01–L03, L05–L07 | ✅ |
| F-L3 | Listar lanzamientos | — | ✅ |
| F-L4 | Buscar lanzamiento por id (con sus canciones) | G01 | ✅ |
| F-L5 | Registrar venta | L09, R05 | ✅ |

### 4.4 Tours

| # | Funcionalidad | Reglas | Estado |
|---|---|---|---|
| F-T1 | Agendar show de un álbum | T01–T03 | ✅ |
| F-T2 | Cancelar show | T04 | ✅ |
| F-T3 | Ver agenda de un álbum | T05 | ✅ |
| F-T4 | Listar álbumes en gira | T06 | ✅ |

### 4.5 Regalías

| # | Funcionalidad | Reglas | Estado |
|---|---|---|---|
| F-R1 | Calcular las regalías de un artista | R01–R04 | ✅ En el menú de Contratos |

---

## 5. Requisitos técnicos y dónde se cumplen

| # | Requisito de la cátedra | Cómo lo cumplimos | Estado |
|---|---|---|---|
| 1 | Diagrama UML | `docs/sello_discografico_uml_v3.pdf` (página 1: dominio; página 2: gestión) | ✅ El diagrama muestra el dominio. `Sello` aparece con tres `Repositorio`; en el código usa los gestores y los DAOs |
| 2 | 4 pilares de la POO | **Herencia:** Artista→Solista/Banda, Lanzamiento→Single/Album, Menu→submenús, `SinContratoVigenteException`→`IllegalStateException`. **Polimorfismo:** `validar()`, `calcularIngresos()`, `getTipo()`, `descripcion()`, `ejecutar()`. **Abstracción:** clases abstractas e interfaces. **Encapsulamiento:** atributos privados, setters con validación y copias defensivas de las listas. | ✅ |
| 3 | Mínimo 5 clases propias | Artista, Solista, Banda, Lanzamiento, Single, Album, Cancion, Contrato, Sello, Gestores, DAOs, Menús | ✅ |
| 4 | 1 clase abstracta + 1 interfaz implementadas | Abstractas: `Artista`, `Lanzamiento`, `Gestor<T>`, `Menu`. Interfaces: `Identificable`, `Dao<T>`, `Tourable` | ✅ |
| 5 | Listas, arreglos o colecciones | `List` de integrantes y canciones, `TreeMap` de la agenda, `Map` en los DAOs, arreglos de opciones en los menús, `Repositorio<T>` | ✅ |
| 6 | **Al menos 3 excepciones propias** | Hay 5: `ArtistaDuplicadoException`, `ContratoVencidoException`, `LanzamientoInvalidoException`, `EntidadNoEncontradaException` y `SinContratoVigenteException` | ✅ |
| 7 | Clase genérica | `Gestor<T extends Identificable>` (la usan los tres gestores), `Repositorio<T extends Identificable>` (álbumes en gira) y la interfaz `Dao<T>` | ✅ |
| 8 | Persistencia JDBC de 2 o 3 clases principales | Artista (+ integrantes), Contrato y Lanzamiento (+ canciones) con DAOs JDBC y transacciones | ✅ |
| 9 | Git y GitHub | Rama por integrante → PR a `dev` → `main`. Cada uno commitea lo suyo | ✅ |
| 10 | JavaFX (opcional) | No se implementa | — |

---

## 6. Decisiones del grupo

Todas las decisiones que estaban pendientes ya se tomaron:

1. **RN-A08:** un artista inactivo **no** puede firmar contratos ni publicar.
2. **RN-C05:** **no** se permiten contratos superpuestos para el mismo artista. Cuenta cualquier contrato no rescindido, incluidos los futuros y los vencidos.
3. **RN-R03 / RN-R04:** las regalías usan el porcentaje del **contrato vigente**. Sin contrato vigente, se informa con `SinContratoVigenteException`.
4. **RN-T06:** los tours se guardan **en memoria** con `Repositorio<Album>`. Es una decisión de alcance: la consigna pide persistir 2 o 3 clases principales y ya se persisten Artista, Contrato y Lanzamiento. Consecuencia: los shows, y lo que suman a las regalías, se pierden al cerrar el programa. Para persistirlos haría falta una tabla `show_tour (id, lanzamiento_id, fecha, ciudad)` con `UNIQUE(lanzamiento_id, fecha)`.
5. **Excepciones propias:** se agregaron `EntidadNoEncontradaException` (reemplaza los `SQLException("No existe...")` de los DAOs) y `SinContratoVigenteException` (en lugar de la `ContratoInvalidoException` propuesta).
6. **RN-A04:** el nombre duplicado se controla contra **todos** los artistas, porque la BD tiene `UNIQUE` sobre `nombre`.
