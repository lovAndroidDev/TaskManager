package com.example.taskmanagement.model

sealed class Page {
	class PendingTask : Page()
	class CompletedTask : Page()
	class CancelledTask : Page()
}