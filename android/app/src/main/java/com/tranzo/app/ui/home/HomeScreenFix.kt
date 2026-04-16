// Add this at the top of HomeScreen composable, before the LazyColumn:

if (state.error != null) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(TranzoColors.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp)
        ) {
            Text(
                text = "Oops!",
                style = MaterialTheme.typography.headlineLarge,
                color = TranzoColors.TextPrimary,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = state.error,
                style = MaterialTheme.typography.bodyMedium,
                color = TranzoColors.TextSecondary,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(24.dp))
            TranzoButton(
                text = "Retry",
                onClick = { viewModel.refresh() }
            )
        }
    }
    return@Composable
}

if (state.isLoading) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(TranzoColors.White),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
    return@Composable
}
