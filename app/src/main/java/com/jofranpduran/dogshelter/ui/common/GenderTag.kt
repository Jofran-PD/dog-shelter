package com.jofranpduran.dogshelter.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jofranpduran.dogshelter.R
import com.jofranpduran.dogshelter.domain.model.Gender
import com.jofranpduran.dogshelter.ui.theme.FemalePink
import com.jofranpduran.dogshelter.ui.theme.FemalePinkContainer
import com.jofranpduran.dogshelter.ui.theme.MaleBlue
import com.jofranpduran.dogshelter.ui.theme.MaleBlueContainer

@Composable
fun GenderTag(
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    gender: Gender
) {
    val (backgroundColor, textColor) = if (gender == Gender.MALE) {
        MaleBlueContainer to MaleBlue
    } else {
        FemalePinkContainer to FemalePink
    }

    Surface(
        modifier = modifier,
        color = backgroundColor,
        shape = shape
    ) {
        Text(
            modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
            text = if (gender == Gender.MALE) stringResource(R.string.male)
                    else stringResource(R.string.female),
            color = textColor,
            style = MaterialTheme.typography.labelSmall
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun GenderTagPreview() {
    Column(modifier = Modifier.padding(16.dp)) {
        GenderTag(gender = Gender.MALE)
        GenderTag(modifier = Modifier.padding(top = 8.dp), gender = Gender.FEMALE)
    }
}