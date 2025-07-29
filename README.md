
# 📱 Prueba Técnica – Desarrollador Android Kotlin Senior

Esta es la solución a la prueba técnica que consiste en desarrollar un módulo de **gestión de tareas offline con sincronización**, utilizando **Clean Architecture, MVI, Room, Hilt y WorkManager**.

---

## 🧠 Arquitectura

Se implementó una arquitectura modular y desacoplada basada en:

- **Clean Architecture** (Separación de capas: `domain`, `data`, `presentation`)
- **MVI** (Model-View-Intent) para la gestión de estados en la UI
- **Room** para almacenamiento local de tareas
- **Hilt** para inyección de dependencias
- **WorkManager** para sincronización en background solo con conexión disponible

```
📦 app/
├── data/
│   ├── local/           # Room (DAO, Database, Entity)
│   ├── repository/      # Implementación del repositorio
├── domain/
│   ├── model/           # Modelo de dominio
│   ├── repository/      # Interfaces de repositorio
│   └── usecase/         # Casos de uso (Add, Observe, Sync)
├── presentation/
│   ├── mvi/             # Intents, State, ViewModel
│   └── ui/              # UI en Jetpack Compose
├── work/                # Worker para sincronización
├── di/                  # Hilt Modules
```

---

## ✅ Funcionalidades

- Crear tareas offline (guardadas en Room)
- Sincronización automática cuando hay conexión
- Evita sincronizar duplicados (usa campo `isSynced`)
- Visualización de tareas no sincronizadas
- Inyección de dependencias con Hilt
- Prueba unitaria para la lógica de sincronización

---

## 🧪 Pruebas

Se implementó una prueba unitaria para el caso de uso `TaskSyncUseCase`, asegurando que:

- Solo se sincronizan tareas no sincronizadas
- Las tareas quedan marcadas como `isSynced`
- No se procesan duplicados

### Ejecutar tests:

```bash
./gradlew test
```

---

## 🧩 Parte 3 – Refactorización y SOLID

Se refactorizó el código propuesto:

```kotlin
class TaskManager {
    var tasks = mutableListOf<Task>()

    fun addTask(title: String, due: String) {
        val t = Task(title, due)
        tasks.add(t)
    }

    fun sync() {
        for (task in tasks) {
            try {
                sendToServer(task)
            } catch (e: Exception) {
                println("Error")
            }
        }
    }
}
```

---

### ✅ Refactor aplicado

- Se separó la lógica de sincronización en la clase `TaskSyncUseCase`.
- Se eliminó la lógica acoplada del `TaskManager`.
- Se inyectó el repositorio para seguir el principio de **Inversión de Dependencias** (D de SOLID).
- Se evitó duplicación de lógica en loops y try/catch.
- Se escribió un **test unitario real** usando un `FakeTaskRepository`.

```kotlin
@Test
fun `syncs only unsynced tasks`() = runTest {
    syncUseCase.syncPendingTasks()

    assertEquals(listOf(1, 2), fakeRepository.syncedTasks)
    assertTrue(fakeRepository.addedTasks.all { it.isSynced })
}
```

---

### 📌 Principios SOLID aplicados

- **S (Responsabilidad única)**: `TaskSyncUseCase` solo sincroniza tareas.
- **O (Abierto/Cerrado)**: puedes extender sincronización sin modificar la lógica.
- **L (Sustitución de Liskov)**: se usa `TaskRepository` como interfaz.
- **I (Segregación de interfaces)**: `TaskRepository` define lo esencial para tareas.
- **D (Inversión de dependencias)**: el Worker depende de una abstracción (`TaskSyncUseCase`), no de la implementación.

---

## 🚀 Cómo ejecutar el proyecto

1. Clona el repositorio
2. Abre en Android Studio
3. Sincroniza dependencias
4. Ejecuta en un dispositivo/emulador
5. Desactiva la conexión para probar modo offline
6. Vuelve a activar la conexión para que se ejecute el `Worker`

---

## 📡 Supuestos y decisiones

- Se simula el envío a servidor mediante `delay()` y `println()`
- Se usa `isSynced` como flag para evitar reenvíos
- La sincronización se dispara desde `MainActivity` y también puede programarse periódicamente
- No se usaron librerías externas para sincronización, siguiendo las restricciones

---

## 🛠️ Desafíos resueltos

- Manejo manual del flujo de sincronización sin bibliotecas externas
- Separación de lógica sincronizable del `Worker` para facilitar testeo
- Prueba unitaria usando repositorio fake sin necesidad de WorkManager real
- Uso correcto de Hilt para inyectar dependencias incluso en el Worker

---

## 📬 Entrega

La solución incluye:
- 📁 Código fuente completo (en archivo zip o repositorio Git)
- 📝 Parte teórica escrita a mano (foto o escaneo)
- ✅ Pruebas implementadas y funcionando
- 📄 Este `README.md` con explicación técnica

---

## 👨‍💻 Autor

Felipe Méndez  
Desarrollador Android Senior  
Contacto: [felipemendez1721@gmail.com]
