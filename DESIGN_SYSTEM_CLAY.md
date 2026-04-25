# Tranzo Claymorphism Design System

## Overview

Tranzo has been redesigned with a **premium claymorphism aesthetic** specifically for Gen Z crypto enthusiasts. The design balances **playful, modern surfaces** with **trust-focused, serious functionality**.

### Design Principles

1. **Trust through Beauty** — Beautiful interfaces build confidence in financial apps
2. **Soft & Generous** — Rounded corners (20-32dp) and ample spacing feel approachable
3. **Vibrant but Refined** — Bold colors softened with gradients and transparency
4. **Depth & Shadows** — Layered shadows (8-12dp elevation) create dimensional depth
5. **Smooth Interactions** — All transitions feel premium and intentional

---

## Color Palette

### Primary Colors (Gradients)
- **Blue** — `#5B8DEF` (primary actions, trust)
- **Purple** — `#9D6BD5` (secondary, playful)
- **Pink** — `#FF6B9D` (accents, highlights)
- **Green** — `#4ECCA3` (success, approval)
- **Yellow** — `#FFD166` (warnings, calls-to-action)

### Light Variants (for gradients)
- **Blue Light** — `#7BA8F7`
- **Purple Light** — `#B89FE0`
- **Pink Light** — `#FF8FB3`

### Neutrals
- **Background** — `#F8F6FF` (soft lavender-white)
- **Surface** — `#F0EDFF` (light lavender)
- **Text Primary** — `#1A1A2E` (deep navy)
- **Text Secondary** — `#5A5F7F` (slate)
- **Text Tertiary** — `#9BA3B8` (light slate)

### Status Colors
- **Success** — `#4ECCA3` (green, transactions)
- **Error** — `#FF6B6B` (red, warnings)
- **Warning** — `#FFB84D` (orange, alerts)
- **Info** — `#5B8DEF` (blue, information)

---

## Shape System

### Corner Radius Scale
```
- Small (buttons, chips): 20dp
- Medium (cards, inputs): 24-28dp
- Large (modals, sheets): 32dp
- Pill (full round): 50dp
```

All components use rounded corners for the claymorphism aesthetic. No sharp corners.

---

## Component Library

### ClayButton
Premium gradient pill button with soft shadow.

```kotlin
ClayButton(
    text = "Send",
    onClick = { /* action */ },
    gradientStart = TranzoColors.PrimaryBlue,
    gradientEnd = TranzoColors.PrimaryPurple,
)
```

**Features:**
- 12dp soft shadow with color-matched ambient
- Linear gradient background
- White text, semibold weight
- 56dp height (standard touch target)

### ClayCard
Soft rounded container with subtle gradient and shadow.

```kotlin
ClayCard(
    backgroundGradient = listOf(
        TranzoColors.BackgroundLight,
        TranzoColors.SurfaceLight
    ),
) {
    // Content here
}
```

**Features:**
- 28dp rounded corners
- Optional gradient background
- 8dp shadow with light ambient
- Clickable with optional onClick

### ClayTextField
Soft rounded input field with subtle shadow.

```kotlin
ClayTextField(
    value = email,
    onValueChange = { email = it },
    placeholder = "Enter your email",
    leadingIcon = { Icon(...) }
)
```

**Features:**
- 24dp rounded corners
- Focus state: blue border
- Unfocused: light gray border
- 4dp shadow

### ClayStatCard
Gradient card for displaying metrics/balances.

```kotlin
ClayStatCard(
    label = "Total Balance",
    value = "$12,450",
    unit = "USD",
    gradientStart = TranzoColors.PrimaryBlue,
    gradientEnd = TranzoColors.PrimaryPurple,
)
```

**Features:**
- 120dp height (or custom)
- Full gradient background
- White text
- 12dp shadow

### ClayActionButton
Icon + text button for quick actions.

```kotlin
ClayActionButton(
    label = "Send",
    onClick = { /* action */ },
    icon = {
        Icon(Icons.Outlined.Send, ...)
    },
    backgroundColor = TranzoColors.PrimaryBlue.copy(alpha = 0.12f)
)
```

**Features:**
- 56dp icon box with gradient
- 8dp shadow on icon
- Label below icon
- Column layout

### ClayBadge
Small label with colored background.

```kotlin
ClayBadge(
    text = "Processing",
    backgroundColor = TranzoColors.PrimaryBlue,
)
```

**Features:**
- 12dp rounded corners
- Semi-transparent background (15% opacity)
- Colored text
- Horizontal padding: 12dp

---

## Screen Implementation Guide

### Example: Dashboard Screen

```kotlin
@Composable
fun DashboardClay() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        TranzoColors.BackgroundLight,
                        TranzoColors.SurfaceLight.copy(alpha = 0.5f)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Header with greeting
            Text(
                "Welcome back",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = TranzoColors.TextPrimary
            )

            // Balance card
            ClayStatCard(
                label = "Total Balance",
                value = "$12,450",
                gradientStart = TranzoColors.PrimaryBlue,
                gradientEnd = TranzoColors.PrimaryPurple,
            )

            // Quick actions
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ClayActionButton(
                    label = "Send",
                    onClick = { /* ... */ },
                    icon = { Icon(...) },
                    modifier = Modifier.weight(1f)
                )
                // More buttons...
            }

            // Transaction list
            repeat(3) { 
                ClayCard { /* transaction */ }
            }
        }
    }
}
```

### Key Patterns

**Spacing:**
- Horizontal padding: 24dp (main screens), 20dp (cards)
- Vertical spacing: 20dp (sections), 12dp (within section)
- Spacer between sections: 32dp

**Gradients:**
- All cards use subtle gradient (background to surface)
- Action buttons use vibrant color gradients
- Background uses lavender gradient

**Shadows:**
- Buttons: 12dp shadow with color-matched ambient
- Cards: 8dp shadow with 8% black ambient
- Input fields: 4dp shadow with 5% black ambient
- Icon boxes: 6dp shadow with color-matched ambient

**Typography:**
- Headings: semibold or bold
- Body: medium weight
- Captions: medium weight (emphasis), regular (secondary)

---

## Implementing on Existing Screens

### Step 1: Update Colors
Replace hardcoded colors with `TranzoColors` from the palette.

### Step 2: Add Background Gradient
Replace solid backgrounds with:
```kotlin
.background(
    brush = Brush.linearGradient(
        colors = listOf(
            TranzoColors.BackgroundLight,
            TranzoColors.SurfaceLight.copy(alpha = 0.5f)
        )
    )
)
```

### Step 3: Replace Components
- `Button` → `ClayButton`
- `Card` → `ClayCard`
- `TextField` → `ClayTextField`
- Custom buttons → `ClayActionButton`
- Large metric displays → `ClayStatCard`

### Step 4: Add Shadows
All interactive elements need shadows:
```kotlin
.shadow(
    elevation = 8.dp,
    shape = RoundedCornerShape(28.dp),
    ambientColor = Color.Black.copy(alpha = 0.08f)
)
```

### Step 5: Adjust Spacing
- Use 24dp horizontal padding
- Use 20dp vertical spacing between items
- Use 32dp between major sections

---

## Screens Already Redesigned

✅ **WelcomeScreenProClay** — Login with email, Google, security messaging
✅ **HomeScreenProClay** — Dashboard with balance cards and quick actions
✅ **SettingsScreenProClay** — Profile, theme selector, security settings

### Screens Still Needing Redesign

⏳ **CardScreenPro** — Physical card management and design
⏳ **TransferScreenPro** — Send crypto flow
⏳ **SwapScreenPro** — Swap interface
⏳ **StreamScreenPro** — Dripper/streaming payments

---

## Animation Guidelines

### Entrance
- Duration: 800ms (fade in on screen load)
- Easing: ease-out

### Button Press
- Duration: 200ms
- Use opacity change only (no layout shift)

### State Change
- Duration: 300ms
- Use gradient color transition for buttons

---

## Accessibility

- Minimum touch target: 48dp × 48dp (all buttons and icons)
- Color contrast: WCAG AA minimum
- No information conveyed by color alone (use icons/text too)
- Include content descriptions for all icons

---

## Implementation Checklist

- [ ] Update TranzoTheme.kt with new colors ✅
- [ ] Update Shape.kt with clay corner radius ✅
- [ ] Create ClayComponents.kt with all components ✅
- [ ] Redesign WelcomeScreenPro ✅
- [ ] Redesign HomeScreenPro ✅
- [ ] Redesign SettingsScreenPro ✅
- [ ] Redesign CardScreenPro
- [ ] Redesign TransferScreenPro
- [ ] Redesign SwapScreenPro
- [ ] Redesign StreamScreenPro
- [ ] Test on multiple screen sizes
- [ ] Verify accessibility (contrast, touch targets)
- [ ] Build and test on device/emulator
- [ ] Gather user feedback

---

## Resources

**Color Reference:**
- All colors defined in `TranzoColors` object
- Use color values consistently across app

**Component Reference:**
- All clay components in `ClayComponents.kt`
- Import with: `import com.tranzo.app.ui.components.*`

**Theme Reference:**
- Theme colors in `TranzoTheme.kt`
- Shapes in `Shape.kt` and `ClayShapes` object

---

## Design Philosophy

> "Beautiful, playful surfaces built on serious, trustworthy foundations"

Tranzo's claymorphism design is intentionally **premium** and **approachable** — matching Gen Z's expectations for modern fintech apps. The soft, rounded aesthetic feels friendly and modern, while the refined color palette and generous spacing convey professionalism and trust.

Every interaction should feel intentional and delightful, but never frivolous. Security and trust are never compromised for aesthetics.
