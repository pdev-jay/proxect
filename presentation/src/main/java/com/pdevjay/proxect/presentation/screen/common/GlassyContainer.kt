package com.pdevjay.proxect.presentation.screen.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlurEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * 유리 같은(Glassy) 효과를 내는 Composable 컨테이너.
 *
 * 이 버전은 컨테이너 뒤의 배경만 블러 처리하고,
 * 컨테이너 내부의 콘텐츠는 선명하게 유지합니다.
 *
 * @param modifier 컨테이너에 적용될 Modifier.
 * @param glassColor 유리의 배경색. 일반적으로 반투명한 흰색 또는 연한 회색을 사용합니다.
 * @param padding 컨테이너 내부 콘텐츠에 적용될 패딩.
 * @param cornerRadius 컨테이너 및 블러 효과의 모서리 곡률.
 * @param blurRadius 컨테이너 뒤의 콘텐츠에 적용될 블러 강도.
 * 참고: BlurEffect는 API 31 (Android 12) 이상에서만 지원됩니다.
 * @param borderColor 유리 컨테이너 주변의 미세한 테두리 색상.
 * @param borderWidth 미세한 테두리의 두께.
 * @param content 유리 컨테이너 내부에 표시될 Composable 콘텐츠.
 */
@Composable
fun GlassyContainer(
    modifier: Modifier = Modifier,
    glassColor: Color = Color.White.copy(alpha = 0.15f), // 반투명한 흰색
    padding: Dp = 16.dp,
    cornerRadius: Dp = 16.dp,
    blurRadius: Dp = 16.dp,
    borderColor: Color = Color.White.copy(alpha = 0.3f),
    borderWidth: Dp = 1.dp,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(cornerRadius)) // 전체 컨테이너를 클립
            .background(Color.Transparent) // 기본 배경은 투명하게 설정
    ) {
        // 1. 블러 효과가 적용될 배경 레이어
        Box(
            modifier = Modifier
//                .fillMaxSize() // 부모 Box의 크기 전체를 채움
                .graphicsLayer {
                    // 이 레이어에만 블러 효과를 적용 (API 31+ 필요)
                    renderEffect = BlurEffect(blurRadius.toPx(), blurRadius.toPx())
                    // 블러 효과가 라운드 코너를 벗어나지 않도록 클립
                    clip = true
                    shape = RoundedCornerShape(cornerRadius)
                }
                .background(glassColor) // 블러 위에 반투명 색상 적용
                .border(
                    width = borderWidth,
                    color = borderColor,
                    shape = RoundedCornerShape(cornerRadius)
                ) // 미세한 테두리 추가
        )

        // 2. 선명한 콘텐츠 레이어 (블러 효과 미적용)
        Box(modifier = Modifier.padding(padding)) { // 콘텐츠에 패딩 적용
            content()
        }
    }
}