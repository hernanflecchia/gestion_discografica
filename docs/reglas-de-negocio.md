# Gestión Discográfica: enunciado y reglas de negocio

> Documento guía del grupo. Describe **qué tiene que hacer** el sistema y **qué reglas tiene que respetar**.
> Cuando una regla cambie, primero se actualiza acá y después se cambia el código.
> Las reglas marcadas con **(a confirmar)** son propuestas que el grupo todavía tiene que aprobar.

---

## 1. Enunciado

Un sello discográfico necesita un sistema de consola para administrar a sus **artistas**, los **contratos** que firma con ellos, los **lanzamientos** (singles y álbumes) que publica y las **giras** de esos lanzamientos. Además, el sistema tiene que calcular las **regalías** que le corresponden a cada artista.

Los artistas pueden ser **solistas** o **bandas**. De todos se registra el nombre artístico, el país de origen y el estado (activo o inactivo). De un solista también se registra su nombre real, y de una banda, la lista de sus integrantes.

Para trabajar con el sello, un artista tiene que firmar un **contrato**, que tiene una fecha de inicio, una fecha de fin y un porcentaje de regalías. El contrato puede estar vigente, vencido o rescindido.

Un artista con contrato vigente puede publicar **lanzamientos**. Un lanzamiento tiene título, fecha, precio, género musical y la cantidad de unidades vendidas. Hay dos tipos:

- **Single:** tiene una sola canción.
- **Álbum:** tiene varias canciones y puede salir de **gira** (tour), con shows agendados en distintas fechas y ciudades.

Cada canción tiene un título y una duración en segundos, y solo existe dentro de un lanzamiento.

Se pide desarrollar en Java un sistema que resuelva los puntos de las secciones 3 y 4, y que cumpla con los requisitos técnicos de la sección 5.

---

## 2. Glosario

| Término | Significado |
|---|---|
| **Artista** | Solista o banda que trabaja con el sello. |
| **Baja de artista** | Pasar al artista a `INACTIVO`. No se borra de la base. |
| **Contrato vigente** | Contrato no rescindido cuya fecha de hoy está entre `fechaInicio` y `fechaFin`. |
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
| RN-A04 | El nombre artístico es **único**: no puede haber dos artistas con el mismo nombre, sin importar mayúsculas y minúsculas. Si se repite, se lanza `ArtistaDuplicadoException`. | `GestorArtista.validar` + `UNIQUE` en la BD |
| RN-A05 | Todo artista nuevo se crea en estado `ACTIVO`. | Constructor de `Artista` |
| RN-A06 | El id lo genera la base de datos y se asigna **una sola vez**. | `Artista.asignarId` |
| RN-A07 | La baja es **lógica**: el artista pasa a `INACTIVO` y conserva sus contratos y lanzamientos (son historial). | `ArtistaDao.eliminar` |
| RN-A08 | Un artista `INACTIVO` no puede firmar contratos ni publicar lanzamientos. **(a confirmar)** | `Sello` |
| RN-A09 | El tipo de artista (solista/banda) no se puede cambiar después del alta. | No hay setter de tipo |

### 3.2 Contratos

| ID | Regla | Dónde se controla |
|---|---|---|
| RN-C01 | Todo contrato pertenece a un artista existente. | `Contrato` + FK en la BD |
| RN-C02 | La fecha de fin tiene que ser **posterior** a la fecha de inicio. | `Contrato` + `CHECK` en la BD |
| RN-C03 | El porcentaje de regalías tiene que ser mayor a 0 y menor o igual a 100. | `Contrato` + `CHECK` en la BD |
| RN-C04 | Solo un artista `ACTIVO` puede firmar un contrato. | `Sello.firmarContrato` |
| RN-C05 | Un artista no puede tener **dos contratos vigentes que se superpongan** en fechas. **(a confirmar)** | `GestorContrato.validar` |
| RN-C06 | El contrato nace `VIGENTE`. | `Sello.firmarContrato` |
| RN-C07 | Un contrato `VIGENTE` pasa a `VENCIDO` cuando la fecha de hoy supera `fechaFin`. | `Contrato.actualizarEstado` |
| RN-C08 | Un contrato se puede **rescindir** solo si está `VIGENTE`. La rescisión es definitiva: un contrato `RESCINDIDO` o `VENCIDO` no vuelve a estar vigente. | `Contrato.rescindir` |
| RN-C09 | `estaVigente()` devuelve `true` solo si el estado no es `RESCINDIDO` y hoy está entre `fechaInicio` y `fechaFin` (inclusive). | `Contrato.estaVigente` |

### 3.3 Lanzamientos y canciones

| ID | Regla | Dónde se controla |
|---|---|---|
| RN-L01 | Título, fecha, género y artista son obligatorios. | `Lanzamiento` (setters) |
| RN-L02 | El precio no puede ser negativo. | `Lanzamiento.setPrecio` + `CHECK` en la BD |
| RN-L03 | Toda canción tiene título y una duración **mayor a 0** segundos. | `Cancion` + `CHECK` en la BD |
| RN-L04 | Un **single** tiene exactamente **1** canción, de hasta **10 minutos** (600 s). | `Single.validar` |
| RN-L05 | Un **álbum** tiene al menos **4** canciones. | `Album.validar` |
| RN-L06 | Si un lanzamiento no cumple las reglas de su tipo, se lanza `LanzamientoInvalidoException`. | `validar()` de cada subclase |
| RN-L07 | Para **publicar** un lanzamiento, su artista tiene que tener un **contrato vigente**. | `Sello.publicarLanzamiento` |
| RN-L08 | Un lanzamiento nuevo arranca con 0 unidades vendidas. | Constructor de `Lanzamiento` |
| RN-L09 | Para registrar una venta, la cantidad tiene que ser mayor a 0, y se **suma** a las unidades vendidas. | `Lanzamiento.registrarVenta` |
| RN-L10 | Las canciones no existen fuera de su lanzamiento (composición): si se elimina el lanzamiento, se eliminan sus canciones. | `ON DELETE CASCADE` en la BD |
| RN-L11 | El tipo de lanzamiento (single/álbum) no se puede cambiar. | No hay setter de tipo |

### 3.4 Tours

| ID | Regla | Dónde se controla |
|---|---|---|
| RN-T01 | Solo los **álbumes** pueden salir de gira (implementan `Tourable`). | `Album implements Tourable` |
| RN-T02 | Para agendar un show, la fecha y la ciudad son obligatorias. | `Album.agendarTour` |
| RN-T03 | No puede haber dos shows del mismo álbum en la misma fecha. | `Album.agendarTour` |
| RN-T04 | Solo se puede cancelar un show que exista. | `Album.cancelarTour` |
| RN-T05 | Los shows se listan ordenados por fecha. | `TreeMap` en `Album` |
| RN-T06 | Los tours se manejan **en memoria** con `Repositorio<T>` (no se persisten). **(a confirmar)** | `Repositorio<T>` |

### 3.5 Ingresos y regalías

| ID | Regla | Dónde se controla |
|---|---|---|
| RN-R01 | Ingresos de un single = `unidadesVendidas × precio`. | `Single.calcularIngresos` |
| RN-R02 | Ingresos de un álbum = `unidadesVendidas × precio + cantidadDeShows × 1000`. | `Album.calcularIngresos` |
| RN-R03 | Regalías de un artista = suma de los ingresos de **todos sus lanzamientos** × `porcentajeRegalias / 100` de su contrato vigente. | `Sello.calcularRegalias` |
| RN-R04 | Si el artista no tiene contrato vigente, sus regalías son 0, o se informa que no tiene contrato. **(a confirmar)** | `Sello.calcularRegalias` |

---

## 4. Funcionalidades (casos de uso)

El sistema arranca en un **menú principal** con las opciones Artistas, Lanzamientos, Contratos, Tours y Salir. Cada submenú vuelve al principal y, después de cada operación, pausa para que se pueda leer el resultado.

Toda la entrada por teclado se valida: si el usuario escribe letras donde va un número, deja un campo vacío o pone una fecha con un formato inválido, el sistema le vuelve a pedir el dato y **no se cierra**. Los errores de negocio se muestran como mensajes claros.

### 4.1 Artistas

| # | Funcionalidad | Reglas | Estado |
|---|---|---|---|
| F-A1 | Alta de solista (nombre, país, nombre real) | A01, A02, A04, A05 | Valida, pero falta conectar con `Sello` |
| F-A2 | Alta de banda (nombre, país, integrantes) | A01, A03, A04, A05 | Valida, pero falta conectar con `Sello` |
| F-A3 | Listar artistas (ordenados por nombre) | — | ✅ |
| F-A4 | Buscar artista por id | — | Recorre la lista: falta usar `sello.buscarArtista` |
| F-A5 | Dar de baja (baja lógica) | A07 | Solo muestra un mensaje |

### 4.2 Contratos

| # | Funcionalidad | Reglas | Estado |
|---|---|---|---|
| F-C1 | Firmar contrato (artista, fechas, porcentaje) | C01–C06, A08 | Pendiente |
| F-C2 | Listar contratos | — | ✅ |
| F-C3 | Actualizar estados (marcar como vencidos los que ya pasaron) | C07 | Pendiente |
| F-C4 | Rescindir contrato | C08 | Pendiente |

### 4.3 Lanzamientos

| # | Funcionalidad | Reglas | Estado |
|---|---|---|---|
| F-L1 | Publicar single (datos + 1 canción) | L01–L04, L06, L07 | Pendiente |
| F-L2 | Publicar álbum (datos + canciones) | L01–L03, L05–L07 | Pendiente |
| F-L3 | Listar lanzamientos | — | ✅ |
| F-L4 | Buscar lanzamiento por id (con sus canciones) | — | Pendiente |
| F-L5 | Registrar venta | L09 | Pendiente |

### 4.4 Tours

| # | Funcionalidad | Reglas | Estado |
|---|---|---|---|
| F-T1 | Agendar show de un álbum | T01–T03 | Pendiente |
| F-T2 | Cancelar show | T04 | Pendiente |
| F-T3 | Ver agenda de un álbum | T05 | Pendiente |

### 4.5 Regalías

| # | Funcionalidad | Reglas | Estado |
|---|---|---|---|
| F-R1 | Calcular las regalías de un artista | R01–R04 | Pendiente (falta decidir en qué menú va) |

---

## 5. Requisitos técnicos y dónde se cumplen

| # | Requisito de la cátedra | Cómo lo cumplimos | Estado |
|---|---|---|---|
| 1 | Diagrama UML | `docs/uml.png` | ✅ (actualizarlo al final) |
| 2 | 4 pilares de la POO | **Herencia:** Artista→Solista/Banda, Lanzamiento→Single/Album, Menu→submenús. **Polimorfismo:** `validar()`, `calcularIngresos()`, `getTipo()`, `ejecutar()`. **Abstracción:** clases abstractas e interfaces. **Encapsulamiento:** atributos privados, setters con validación y copias defensivas de las listas. | ✅ |
| 3 | Mínimo 5 clases propias | Artista, Solista, Banda, Lanzamiento, Single, Album, Cancion, Contrato, Sello, Gestores, DAOs, Menús | ✅ |
| 4 | 1 clase abstracta + 1 interfaz implementadas | Abstractas: `Artista`, `Lanzamiento`, `Gestor<T>`, `Menu`. Interfaces: `Identificable`, `Dao<T>`, `Tourable` | ✅ |
| 5 | Listas, arreglos o colecciones | `List` de integrantes y canciones, `TreeMap` de la agenda, `Map` en los DAOs, arreglos de opciones en los menús | ✅ |
| 6 | **Al menos 3 excepciones propias** | Hay 2: `ArtistaDuplicadoException` y `LanzamientoInvalidoException`. Propuestas: `EntidadNoEncontradaException` (reemplaza los `SQLException("No existe...")` de los DAOs) y `ContratoInvalidoException` (sin contrato vigente, fechas o porcentaje inválidos) | ⚠️ Falta 1 como mínimo |
| 7 | Clase genérica | `Gestor<T extends Identificable>`, `Dao<T>`, `Repositorio<T>` | ✅ (`Repositorio<T>` está incompleto) |
| 8 | Persistencia JDBC de 2 o 3 clases principales | Artista (+ integrantes), Contrato y Lanzamiento (+ canciones) con DAOs JDBC y transacciones | ✅ (`ContratoDao.modificar` no va a funcionar hasta que `Contrato` tenga id real: hoy `getId()` devuelve 0) |
| 9 | Git y GitHub | Rama por integrante → PR a `dev` → `main`. Cada uno commitea lo suyo | ✅ |
| 10 | JavaFX (opcional) | No se implementa | — |

**Condiciones generales para tener en cuenta:**
- La defensa es **individual**: cada integrante tiene que poder explicar **todo** el sistema, no solo su parte. Este documento sirve para estudiar.
- El código tiene que estar **documentado y comentado**.
- Entrega: COM 9 el 17/11 y el 18/11 (recuperatorio el 24/11). COM 8 el 18/11 y el 19/11 (recuperatorio el 25/11).

---

## 6. Flujo de una operación (ejemplo: publicar un álbum)

```
MenuLanzamientos      lee los datos con ConsolaUtil y arma el Album con sus canciones
      │
      ▼
Sello.publicarLanzamiento(album)
      │ 1. ¿El artista está ACTIVO y tiene contrato vigente?  (RN-A08, RN-L07)
      │ 2. album.validar() → ¿tiene ≥ 4 canciones?            (RN-L05)
      ▼
GestorLanzamiento.alta(album)  →  validar()
      │
      ▼
LanzamientoDao.agregar(album)
      │ BEGIN
      │ INSERT lanzamiento  → recupera el id generado
      │ INSERT cancion × n
      │ COMMIT  (si algo falla: ROLLBACK, no queda nada a medias)
      ▼
MySQL
```

Si cualquier paso lanza una excepción, sube hasta `Menu.mostrar()`, que muestra el mensaje y vuelve al menú sin cortar el programa.

---

## 7. Decisiones pendientes del grupo

1. **RN-A08:** ¿un artista inactivo puede firmar contratos o publicar? Propuesta: no.
2. **RN-C05:** ¿se permiten contratos superpuestos para el mismo artista? Propuesta: no.
3. **RN-R03 / RN-R04:** ¿qué porcentaje se usa para las regalías si el artista tuvo varios contratos? Propuesta: el del contrato vigente; sin contrato vigente, se informa.
4. **RN-T06:** ¿los tours se guardan en memoria (`Repositorio<T>`) o en una tabla? Propuesta: en memoria, así `Repositorio<T>` tiene un uso real.
5. **Tercera excepción:** ¿`EntidadNoEncontradaException`, `ContratoInvalidoException` o las dos?
6. **RN-A04:** ¿el duplicado se controla solo contra los artistas activos o contra todos? Propuesta: contra todos, porque la BD tiene `UNIQUE` sobre `nombre`.
