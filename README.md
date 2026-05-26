# ChefAI 👨‍🍳 

**ChefAI** es una aplicación móvil nativa para Android diseñada para los amantes de la cocina. Permite a los usuarios explorar una amplia variedad de platos, buscar recetas específicas en tiempo real y visualizar de forma detallada los ingredientes necesarios junto con las instrucciones paso a paso para su preparación.



##  Características Principales

* **Pantalla de Bienvenida (Splash Screen):** Una entrada fluida y estética con animación de presentación al abrir la app.
* **Buscador Inteligente (Lupa):** Barra de búsqueda integrada en la pantalla principal que filtra las recetas conectándose directamente a la API según lo que escriba el usuario.
* **Lista Dinámica de Recetas:** Interfaz moderna y optimizada que carga imágenes de los platillos de forma asíncrona.
* **Vista de Detalles Completa:** Al presionar cualquier receta, se despliegan de forma organizada todos sus ingredientes y la guía de preparación con soporte para scroll.
* **Manejo de Errores y Carga:** Control de estados en pantalla (Loading, Success, Error) para una experiencia de usuario robusta ante fallos de conexión.



##  Arquitectura y Tecnologías Utilizadas

El proyecto fue desarrollado bajo los estándares de desarrollo más modernos de la industria Android:

* **Lenguaje:** Kotlin 
* **Interfaz de Usuario:** Jetpack Compose (UI 100% Declarativa)
* **Arquitectura:** Clean Architecture (Separación por capas: Data, Domain, Presentation)
* **Patrón de Diseño:** MVVM (Model-View-ViewModel)
* **Conexión a Red:** Retrofit & Gson (Consumo de la API REST de *TheMealDB*)
* **Carga de Imágenes:** Coil (Asynchronous Image Loading)
* **Gestión de Estados:** StateFlow & Coroutines (Programación asíncrona y reactiva)
* **Navegación:** Jetpack Navigation Compose
