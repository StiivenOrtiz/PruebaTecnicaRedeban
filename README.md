---

# Prueba Técnica Redeban - Android

Este proyecto es una aplicación móvil para la resolución de la Prueba Técnica ed Android en Redeban,
esta aplicación permtie tener conexión con una api que autoriza pagos o anulaciones, a su vez 
visualizarla historial de las transacciones realizadas.

---

## Arquitectura y Decisiones de Diseño

La aplicación sigue los principios de **Clean Architecture** y **MVVM (Model-View-ViewModel)**,
separando las responsabilidades en capas definidas para facilitar el testing y el mantenimiento.

---

### Capas del Proyecto:

1. **Data Layer:** Implementa el patrón *Repository*. Maneja la persistencia local con **Room** y la
   comunicación remota con **Retrofit**. Incluye interceptores para seguridad y manejo de tokens.
2. **Domain Layer:** La capa más pura del proyecto. Contiene los modelos de negocio, las interfaces
   de los repositorios y los **Use Cases** (Interactors) que dictan la lógica de negocio.
3. **View (UI) Layer:** Construida con **Jetpack Compose**. Utiliza *StateFlow* para el
   manejo de estados reactivos y *ViewModels* para desacoplar la lógica de la interfaz.

---

### Decisiones Clave:

* **Seguridad:** Se implementó un SecurityProcessor con soporte para múltiples estrategias de
  hashing (SHA-256/SHA-3) mediante el patrón **Strategy** y calificadores de Hilt, asegurando que
  los datos sensibles (como el PAN de la tarjeta) nunca se almacenen en texto plano.
* **Modularidad de DI:** El uso de **Hilt** está segmentado por módulos (NetworkModule,
  DatabaseModule, DataModule y SecurityModule) para un control granular de las dependencias.
* **Reactividad:** Se utiliza Flow y StateFlow en toda la cadena (desde Room hasta la UI) para
  garantizar que la interfaz siempre refleje el estado actual de los datos.

---

## Stack Tecnológico Detallado

Se han seleccionado librerías de última generación para garantizar estabilidad y modernidad:

### Interfaz de Usuario y Navegación

* **Jetpack Compose:** Motor de UI declarativo para una construcción ágil y
  reactiva.
* **Material 3:** Implementación de los últimos estándares de diseño de Google.
* **Navigation Compose:** Gestión de rutas con **Type Safety**, evitando errores de argumentos en
  tiempo de ejecución.
* **ConstraintLayout Compose:** Optimización de jerarquías de vistas complejas.

### Inyección de Dependencias

* **Hilt (v2.59.2):** Implementado con Dagger, simplifica la inyección de dependencias con un enfoque
  específico para Android.

### Red y Serialización

* **Retrofit 3.0.0:** Cliente REST para la comunicación con el backend.
* **OkHttp 5.3.2:** Gestión de peticiones HTTP con interceptores personalizados para seguridad y
  logs.
* **Kotlinx Serialization:** Serialización de JSON nativa de Kotlin, más rápida y segura que Gson.

### Persistencia de Datos

* **Room (v2.8.4):** Capa de abstracción sobre SQLite que permite el acceso a datos local de forma
  robusta, con soporte nativo para **Flow**.

### Testing

* **MockK:** Librería de mocking nativa para Kotlin, utilizada para simular comportamientos en Use
  Cases y Repositorios.
* **Hilt Testing:** Permite la inyección de dependencias en tests instrumentados para realizar
  pruebas de integración reales.
* **Compose UI Test:** Verificación de jerarquías de nodos y comportamiento de la interfaz de
  usuario.

---

## Configuración

### 1. Requisitos Previos

* Android Studio Ladybug (o superior).
* JDK 17 o 21.
* Gradle 9.2.1 (gestionado por el Wrapper // DSL).

### 2. Secrets

El proyecto utiliza una función personalizada para leer variables de entorno. Debes crear (o editar)
el archivo local.properties en la raíz del proyecto y añadir:

```properties

BASE_URL={servidor compatible con el consumo del api}
AUTHORIZATION={secret auth para el servidor}

```

*Si estas variables no se encuentran, el build utilizará valores por defecto configurados en
el build.gradle.*

### 3. Compilación

Puedes compilar el proyecto directamente desde Android Studio o mediante la terminal:

```bash

# Limpiar y compilar
./gradlew clean build

# Instalar en dispositivo conectado
./gradlew installDebug
```

---

## Estructura de Paquetes Principal

* `data/`: DTOs, DAOs, Entidades, Mappers y Repositorios.
* `domain/`: Modelos de dominio, Casos de Uso e interfaces.
* `view/`: Pantallas de Compose (Dashboard, Payment, Detail), ViewModels y estados.
* `security/`: Lógica de cifrado y procesamiento de huellas digitales de tarjetas.

```bash
pruebatecnicaredeban
    │
    │   MainActivity.kt
    │   PruebaTecnicaRedebanApp.kt
    │
    ├───data
    │   ├───datasource
    │   │   ├───database
    │   │   │   │   TransactionsDatabase.kt
    │   │   │   │
    │   │   │   ├───dao
    │   │   │   │       TransactionDao.kt
    │   │   │   │
    │   │   │   ├───entities
    │   │   │   │       TransactionEntity.kt
    │   │   │   │
    │   │   │   └───mapper
    │   │   │           TransactionEntityMapper.kt
    │   │   │
    │   │   └───network
    │   │       ├───api
    │   │       │       TransactionApiService.kt
    │   │       │
    │   │       ├───dto
    │   │       │   ├───request
    │   │       │   │       AnnulmentRequest.kt
    │   │       │   │       AuthorizationRequest.kt
    │   │       │   │
    │   │       │   └───response
    │   │       │           AnnulmentResponse.kt
    │   │       │           AuthorizationResponse.kt
    │   │       │
    │   │       ├───interceptors
    │   │       │       TransactionInterceptor.kt
    │   │       │
    │   │       └───parser
    │   │               HexParser.kt
    │   │
    │   ├───di
    │   │       DatabaseModule.kt
    │   │       DataModule.kt
    │   │       NetworkModule.kt
    │   │       SecurityModule.kt
    │   │
    │   ├───repository
    │   │       AnnulmentTransactionRepositoryImpl.kt
    │   │       AuthorizeTransactionRepositoryImpl.kt
    │   │       TransactionRepositoryImpl.kt
    │   │
    │   ├───security
    │   │   │   HashStrategy.kt
    │   │   │   SecurityProcessor.kt
    │   │   │   Sha256HashStrategy.kt
    │   │   │   Sha3HashStrategy.kt
    │   │   │
    │   │   └───annotations
    │   │           Sha256.kt
    │   │           Sha3.kt
    │   │
    │   └───utils
    │           PanUtils.kt
    │
    ├───domain
    │   ├───mapper
    │   │       TransactionModelMapper.kt
    │   │
    │   ├───model
    │   │       TransactionInternalStatus.kt
    │   │       TransactionModel.kt
    │   │       TransactionOperationType.kt
    │   │       TransactionStatus.kt
    │   │
    │   ├───repository
    │   │       AnnulmentTransactionRepository.kt
    │   │       AuthorizeTransactionRepository.kt
    │   │       TransactionRepository.kt
    │   │
    │   └───usecase
    │           GetTransactionsUseCase.kt
    │           GetTransactionUseCase.kt
    │           ProcessTransactionUseCase.kt
    │
    ├───ui
    │   └───theme
    │           Color.kt
    │           Theme.kt
    │           Type.kt
    │
    └───view
        ├───core
        │   ├───components
        │   │   ├───dashboard
        │   │   │       TransactionCard.kt
        │   │   │       TransactionColumnDescription.kt
        │   │   │
        │   │   ├───paymentamount
        │   │   │       NumericKeyboard.kt
        │   │   │
        │   │   └───transactiondetail
        │   │           DetailRow.kt
        │   │
        │   ├───mapper
        │   │       TransactionUiStatusMapper.kt
        │   │       TransactionUiTypeMapper.kt
        │   │
        │   ├───model
        │   │       PaymentInput.kt
        │   │       PaymentProcess.kt
        │   │       PaymentStatus.kt
        │   │       TransactionUiModel.kt
        │   │       TransactionUiStatus.kt
        │   │       TransactionUiType.kt
        │   │
        │   └───navigation
        │       │   NavigationWrapper.kt
        │       │   Screens.kt
        │       │
        │       └───types
        │               PaymentInputType.kt
        │
        ├───dashboard
        │       DashboardScreen.kt
        │       DashboardUiState.kt
        │       DashboardViewModel.kt
        │
        ├───paymentamount
        │       PaymentAmountScreen.kt
        │       PaymentAmountViewModel.kt
        │
        ├───paymentstatus
        │       PaymentStatusScreen.kt
        │       PaymentStatusUiState.kt
        │       PaymentStatusViewModel.kt
        │
        └───transactiondetail
                TransactionDetailScreen.kt
                TransactionDetailUiState.kt
                TransactionDetailViewModel.kt

```

---
