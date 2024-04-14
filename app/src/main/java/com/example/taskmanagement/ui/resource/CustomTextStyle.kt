package com.example.taskmanagement.ui.resource

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

object CustomTextStyle {
	val text14spBlack: TextStyle = TextStyle(
		fontSize = 14.sp,
		fontFamily = FontFamily.Monospace,
		color = CustomColors.black1
	)
	
	val text12spBlack: TextStyle = TextStyle(
		fontSize = 12.sp,
		fontFamily = FontFamily.Default,
		color = CustomColors.black1
	)
	
	val text12spWhite: TextStyle = TextStyle(
		fontSize = 12.sp,
		fontFamily = FontFamily.Default,
		color = Color.White
	)
	
	val text12spWhiteBold: TextStyle = TextStyle(
		fontSize = 12.sp,
		fontFamily = FontFamily.Default,
		fontWeight = FontWeight.Bold,
		color = Color.White
	)
	
	val text14spBlackBold: TextStyle = TextStyle(
		fontSize = 14.sp,
		fontFamily = FontFamily.Default,
		fontWeight = FontWeight.Bold,
		color = Color.Black
	)
}