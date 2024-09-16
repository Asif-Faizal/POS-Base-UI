package com.example.cgs_pos

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val gesturesEnabled = remember { mutableStateOf(true) }

    val currentTime = remember { mutableStateOf("") }
    val currentDate = remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        while (true) {
            val time = LocalTime.now()
            val date = LocalDate.now()
            val timeFormatter = DateTimeFormatter.ofPattern("h:mm a")
            val dateFormatter = DateTimeFormatter.ofPattern("MMMM dd")
            currentTime.value = time.format(timeFormatter)
            currentDate.value = date.format(dateFormatter)
            delay(1000) // update every 1 second
        }
    }

    ModalNavigationDrawer(
        gesturesEnabled = gesturesEnabled.value,
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                onLogoutClick = {
                    // Handle logout click
                }
            )
        },
        scrimColor = Color.Transparent,
        ) {
        Scaffold(
            containerColor = Color.White,
            topBar = {
                TopAppBar(
                    actions = {
                        IconButton(onClick = { /* Handle notification click */ }) {
                            Icon(
                                imageVector = Icons.Filled.Notifications,
                                contentDescription = "Notification"
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            coroutineScope.launch {
                                if (drawerState.isOpen) {
                                    drawerState.close() // Close the drawer if already open
                                } else {
                                    drawerState.open() // Open the drawer if it is closed
                                }
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = "Drawer"
                            )
                        }
                    },
                    title = {
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp), contentAlignment = Alignment.Center) {
                            Text("CGS-POS", style = MaterialTheme.typography.titleLarge.copy(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            ))
                        }
                    },
                    colors = TopAppBarColors(
                        containerColor = Color.White,
                        titleContentColor = Color.Black,
                        scrolledContainerColor = MaterialTheme.colorScheme.primary,
                        actionIconContentColor = Color.Black,
                        navigationIconContentColor = Color.Black
                    )
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(30.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.entrepreneur),
                        contentDescription = "Dashboard Image",
                        modifier = Modifier.size(150.dp)
                    )
                    Text(
                        currentTime.value,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(top = 30.dp)
                    )
                    Text(
                        currentDate.value,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Normal
                        ),
                        modifier = Modifier.padding(top = 10.dp)
                    )
                }

                // Grid view
                val options = listOf("Purchase", "Void", "Balance", "Transaction")
                val optionsIcon = listOf(
                Pair("Purchase", R.drawable.purchase),
                Pair("Void", R.drawable.resource_void),
                Pair("Balance", R.drawable.balance),
                Pair("Transaction", R.drawable.transaction)
            )
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(optionsIcon) { (option, imageResId) ->
                        OptionCard(
                            option = option,
                            imageResId = imageResId,  // Pass the drawable resource ID
                            onClick = {
                                // Handle option click
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DrawerContent(onLogoutClick: () -> Unit) {
    // Use a Box with a fixed width to ensure the drawer doesn't exceed the screen width
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .width(240.dp)
            .background(Color.White)
            .padding(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.fillMaxSize()
        ) {
            // Header with account information
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Replace with actual profile picture or avatar
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = "Profile",
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(
                    verticalArrangement = Arrangement.Top,
                ) {
                    Text("John Doe", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text("6786235", fontWeight = FontWeight.Normal, fontSize = 14.sp)
                }
            }
            Text("john.doe@example.com", color = Color.Gray)
            Divider(
                modifier = Modifier.padding(vertical = 20.dp),
                thickness = 0.5.dp, color = Color.Blue.copy(alpha = 0.5f))
            Spacer(modifier = Modifier.weight(1f))
            ElevatedButton(
                elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 20.dp, pressedElevation = (-20).dp),
                shape = RoundedCornerShape(10.dp),
                onClick = {

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(horizontal = 10.dp, vertical = 10.dp),
                colors = ButtonColors(
                    containerColor = Color.Blue,
                    contentColor = Color.White,
                    disabledContainerColor = Color.Blue.copy(alpha = 0.1f),
                    disabledContentColor = Color.White.copy(alpha = 0.1f)
                )
            ) {
                Text("Settings", fontSize = 20.sp)
            }
            ElevatedButton(
                elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 20.dp, pressedElevation = (-20).dp),
                shape = RoundedCornerShape(10.dp),
                onClick = {

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(horizontal = 10.dp, vertical = 10.dp),
                colors = ButtonColors(
                    containerColor = Color.Red,
                    contentColor = Color.White,
                    disabledContainerColor = Color.Red.copy(alpha = 0.1f),
                    disabledContentColor = Color.White.copy(alpha = 0.1f)
                )
            ) {
                Text("Logout", fontSize = 20.sp)
            }
        }
    }
}

@Composable
fun OptionCard(option: String, onClick: () -> Unit,@DrawableRes imageResId: Int) {
    Card(
        shape = RoundedCornerShape(10.dp),
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = Color.Blue),
        elevation = CardDefaults.cardElevation(3.dp),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .height(120.dp),
            contentAlignment = Alignment.Center
        ) {
            Column (
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Image(
                    painter = painterResource(id = imageResId),
                    contentDescription = "Option Image",
                    modifier = Modifier.size(30.dp).padding(bottom = 10.dp)
                )
                Text(
                    text = option,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview(navController: NavController) {
    val navController = rememberNavController()
    DashboardScreen(navController = navController)
}
