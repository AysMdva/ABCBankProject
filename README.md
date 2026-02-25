# ABC Bank Carousel

A simple Android app that shows a carousel of images, page indicators, a search bar, and a scrollable list of items. You can filter the list by typing in the search field.

![App screenshot](docs/screenshot.svg)

## What it contains

- **Image carousel** — Swipeable header with multiple pages and dot indicators
- **Search** — Filter list items by title or subtitle
- **Item list** — RecyclerView of items (thumbnail, title, subtitle) for the current page
- **Statistics** — FAB opens a bottom sheet with per-page stats (item count, top characters)

Built with Kotlin, View Binding, RecyclerView, ViewPager2, and a simple MVVM-style setup (ViewModel + use cases for filtering and statistics).
