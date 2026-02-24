# ABCBankCarousel

Android demo app for a banking client: image carousel, scrollable filtered list, and statistics bottom sheet. Implemented twice—**XML (View)** and **Jetpack Compose**—on separate branches for comparison.

## Requirements

- **Android Studio** Ladybug (2024.2.1) or newer (or compatible IDE)
- **JDK 17**
- **minSdk 24**, **targetSdk 35**

## Project structure

- **Clean Architecture + MVVM**
  - **Domain:** `domain/model/`, `domain/usecase/` (no Android deps)
  - **Presentation:** shared `MainViewModel`; UI split by branch (XML vs Compose)
  - **No data layer** (in-memory mock data)
- **Package:** `com.abcbank.carousel`

## Branches and how to run

| Branch | UI stack | Launcher activity |
|--------|----------|--------------------|
| `main` | — | None (foundation only) |
| `feature/xml-implementation` | View + ViewPager2 + RecyclerView + ViewBinding | `XmlMainActivity` |
| `feature/compose-implementation` | Compose (HorizontalPager, LazyColumn, state hoisting) | `ComposeMainActivity` |

**To run the app in Android Studio:**

1. Clone and open the project.
2. Check out the UI branch you want:
   - **XML:**  
     `git checkout feature/xml-implementation`
   - **Compose:**  
     `git checkout feature/compose-implementation`
3. **File → Sync Project with Gradle Files.**  
   If the Gradle wrapper jar is missing, use **File → Build → Generate Gradle Wrapper** or install [Gradle 8.5+](https://gradle.org/install/) and run `gradle wrapper --gradle-version=8.5` in the project root.
4. Run on a device or emulator (minSdk 24).

## Features (both branches)

- **Image carousel** – swipe to change page; list content and page indicators update with the current page.
- **Scrollable list** – per-page item counts: 25, 30, 20, 15, 28. Each item: thumbnail, title, subtitle (fruit names).
- **Search** – pinned at top; filters by title or subtitle (case-insensitive, real-time).
- **FAB** – opens bottom sheet with stats for all pages: page label, item count, top 3 character counts.

## Quality

- **State:** ViewModel uses `StateFlow`; Compose uses `collectAsStateWithLifecycle()` and hoisted state/callbacks.
- **Lifecycle:** No activity/fragment references in ViewModel; binding cleared in `onDestroyView` (XML); `viewModelScope` for coroutines.
- **Lists:** RecyclerView with `DiffUtil` (XML); LazyColumn with stable `key` (Compose).
- **Tests:** Unit tests for `FilterItemsUseCase` and `CalculateStatisticsUseCase` under `app/src/test/`.

## License

Internal use; banking client.
