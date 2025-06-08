package com.team6.smartbudget.features.chart.presentation

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.*
import androidx.core.graphics.toColorInt
import com.team6.smartbudget.sma1.R

data class ChartSector(
    val value: Float,
    val color: Int,
    val label: String = "",
    val iconRes: Int? = null
)

data class ChartData(
    val icon: Int,
    val title: String,
    val subtitle: String,
    val sectors: List<ChartSector>
)

data class ChartOptions(
    val iconSize: Float = 48f,
    val ringWidth: Float = 48f,
    val ringSpacing: Float = 8f,
    val percentSize: Float = 48f,
    val titleSize: Float = 96f,
    val subtitleSize: Float = 48f,
    val selectedBrightnessMultiplier: Float = 1.3f
)

class CircularChartView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var sectors: List<ChartSector> = emptyList()
    private var selectedSectorIndex: Int = -1
    private var data: ChartData? = null
    private var options: ChartOptions = ChartOptions()
        set(value) {
            field = value
            percentPaint.textSize = value.percentSize
            centerTextPaint.textSize = value.titleSize
            centerSubtextPaint.textSize = value.subtitleSize
        }

    private val ringPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
    }

    private val percentPaint = Paint().apply {
        isAntiAlias = true
        textAlign = Paint.Align.LEFT
        typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
    }

    private val centerTextPaint = Paint().apply {
        isAntiAlias = true
        textAlign = Paint.Align.CENTER
        typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        color = "#666666".toColorInt()
    }

    private val centerSubtextPaint = Paint().apply {
        isAntiAlias = true
        textAlign = Paint.Align.CENTER
        color = "#999999".toColorInt()
    }

    private var centerX = 0f
    private var centerY = 0f
    private var maxRadius = 0f
    private var ringThickness = 0f
    private var centerRadius = 0f

    init {
        setChartOptions(ChartOptions())
    }

    fun setData(chartData: ChartData) {
        data = chartData
        setSectors(chartData.sectors)
    }

    fun setChartOptions(options: ChartOptions) {
        this.options = options
        invalidate()
    }

    private fun setSectors(sectorList: List<ChartSector>) {
        require(sectorList.map { it.color }.toSet().size == sectorList.size) {
            "All sector colors must be unique"
        }
        require(sectorList.size in 2..7) {
            "Number of sectors must be between 2 and 7"
        }
        sectorList.forEach { sector ->
            require(sector.value in 1f..100f) {
                "Sector values must be between 1 and 100"
            }
        }

        sectors = sectorList
        selectedSectorIndex = -1
        requestLayout()
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        centerX = w * 0.45f
        centerY = h / 2f

        val availableRadius = minOf(centerX - 50f, centerY - 50f)

        if (sectors.isNotEmpty()) {
            ringThickness = availableRadius * 0.12f
            centerRadius = availableRadius * 0.25f
            maxRadius = centerRadius + sectors.size * (ringThickness + options.ringSpacing)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (sectors.isEmpty()) return

        drawRings(canvas)
        drawCenterArea(canvas)
        drawPercentageLabels(canvas)
        drawIconBar(canvas)
    }

    private fun drawRings(canvas: Canvas) {
        sectors.forEachIndexed { index, sector ->
            val radius = centerRadius + (index + 1) * (ringThickness + options.ringSpacing)
            val sweepAngle = (sector.value / 100f) * 360f

            val isSelected = selectedSectorIndex == index
            val color = if (isSelected) {
                brightenColor(sector.color, options.selectedBrightnessMultiplier)
            } else {
                sector.color
            }

            ringPaint.apply {
                this.color = color
                strokeWidth = ringThickness
            }

            val rectF = RectF(
                centerX - radius,
                centerY - radius,
                centerX + radius,
                centerY + radius
            )

            canvas.drawArc(rectF, 90f, sweepAngle, false, ringPaint)
        }
    }

    private fun drawCenterArea(canvas: Canvas) {
        val iconCenterY = centerY - options.titleSize / 2
        val icon = context.getDrawable(R.drawable.ic_outline_warning_24)
        icon?.setBounds(
            (centerX - options.iconSize / 2).toInt(), (iconCenterY - options.iconSize / 2).toInt(),
            (centerX + options.iconSize / 2).toInt(), (iconCenterY + options.iconSize / 2).toInt()
        )
        icon?.draw(canvas)

        val titleY = iconCenterY + options.iconSize * 2
        canvas.drawText(data?.title.orEmpty(), centerX, titleY, centerTextPaint)

        val subtitleY = titleY + options.titleSize / 2
        canvas.drawText(data?.subtitle.orEmpty(), centerX, subtitleY, centerSubtextPaint)
    }

    private fun drawPercentageLabels(canvas: Canvas) {
        val labelStartX = centerX + maxRadius + 40f
        val totalHeight = sectors.size * 70f
        var currentY = centerY - totalHeight / 2f + 35f

        sectors.forEachIndexed { index, sector ->
            val isSelected = selectedSectorIndex == index
            val color = if (isSelected) {
                brightenColor(sector.color, options.selectedBrightnessMultiplier)
            } else {
                sector.color
            }

            val dotRadius = if (isSelected) 7f else 5f
            val dotPaint = Paint().apply {
                style = Paint.Style.FILL
                this.color = color
                isAntiAlias = true
            }
            canvas.drawCircle(labelStartX, currentY - 12f, dotRadius, dotPaint)

            percentPaint.color = color
            val percentText = "${sector.value.toInt()}%"
            canvas.drawText(percentText, labelStartX + 20f, currentY, percentPaint)

            currentY += 70f
        }
    }

    private fun drawIconBar(canvas: Canvas) {
        val iconBarX = centerX
        val iconSize = options.iconSize
        val totalHeight = sectors.size * 70f
        var iconY = centerY + totalHeight * 3 / 4

        sectors.forEach { sector ->
            val iconCenterY = iconY - 12f
            if (sector.iconRes != null) {
                val icon = context.getDrawable(sector.iconRes)
                icon?.setBounds(
                    /* left = */ (iconBarX - iconSize / 2).toInt(),
                    /* top = */ (iconCenterY - iconSize / 2).toInt(),
                    /* right = */ (iconBarX + iconSize / 2).toInt(),
                    /* bottom = */ (iconCenterY + iconSize / 2).toInt()
                )
                icon?.draw(canvas)
            }
            iconY += 70f
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val dx = event.x - centerX
            val dy = event.y - centerY
            val distance = sqrt(dx * dx + dy * dy)

            if (distance > centerRadius && distance <= maxRadius) {
                val ringIndex =
                    ((distance - centerRadius) / (ringThickness + options.ringSpacing)).toInt()

                if (ringIndex < sectors.size) {
                    var touchAngle = Math.toDegrees(atan2(dy.toDouble(), dx.toDouble())).toFloat()
                    touchAngle = (touchAngle + 360f + 270f) % 360f

                    val sector = sectors[ringIndex]
                    val sectorSweep = (sector.value / 100f) * 360f

                    if (touchAngle <= sectorSweep) {
                        selectedSectorIndex =
                            if (selectedSectorIndex == ringIndex) -1 else ringIndex
                        invalidate()
                        performClick()
                        return true
                    }
                }
            }
        }
        return super.onTouchEvent(event)
    }

    override fun performClick(): Boolean {
        super.performClick()
        return true
    }

    private fun brightenColor(color: Int, factor: Float): Int {
        val hsv = FloatArray(3)
        Color.colorToHSV(color, hsv)
        hsv[2] = (hsv[2] * factor).coerceIn(0f, 1f)
        hsv[1] = (hsv[1] * 0.8f).coerceIn(0f, 1f)
        return Color.HSVToColor(hsv)
    }
}