package me.erfandp.buttondp

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.res.getResourceIdOrThrow

class CustomizableGenericButton(
	context: Context,
	attrs: AttributeSet? = null,
	defStyleAttr: Int = 0
): ConstraintLayout(context, attrs, defStyleAttr) {
	constructor(context: Context, attrs: AttributeSet?): this(context, attrs, 0) {}
	
	//attributes
	private var buttonType = ButtonTypes.ONLY_TITLE
	private var buttonStyles = ButtonStyles.NORMAL
	private var buttonTitleText = context.getString(R.string.button_title)
	private var buttonTitleTextColor = ContextCompat.getColor(context, R.color.buttondp_text_normal)
	private var buttonSubtitleText = context.getString(R.string.button_subtitle)
	private var buttonSubtitleTextColor =
		ContextCompat.getColor(context, R.color.buttondp_text_normal)
	private var buttonIconRef = R.drawable.erfandp_logo_final_form
	private var buttonIconRoundedCorner = true
	private var buttonBackgroundTint: Int =
		ContextCompat.getColor(context, R.color.buttondp_style_normal)
	
	//views initialized in init block
	private val buttonTitleView: TextView
	private val buttonSubtitleView: TextView
	private val buttonIconView: ImageView
	private val buttonRoot: ConstraintLayout
	
	//high-order function invoked on touch action ACTION.UP
	var onClickListener: (() -> Unit)? = null
	
	init {
		val view = View.inflate(context, R.layout.customizable_generic_button_layout, this)
		//for preventing animation effects from showing on corners of view
		view.clipToOutline = true
		
		//find and initialize views
		buttonTitleView = view.findViewById(R.id.buttondp_title)
		buttonSubtitleView = view.findViewById(R.id.buttondp_subtitle)
		buttonIconView = view.findViewById(R.id.buttondp_icon)
		buttonRoot = view.findViewById(R.id.buttondp_root)
		
		initAttributes(attrs, context)
		initViews()
	}
	
	private fun initViews() {
		initButtonStyles(buttonStyles)
		initButtonTexts()
		initButtonIcon()
		initButtonType(buttonType)
	}
	
	private fun initButtonTexts() {
		//init title
		buttonTitleView.text = buttonTitleText
		buttonTitleView.setTextColor(buttonTitleTextColor)
		//init subtitle
		buttonSubtitleView.text = buttonSubtitleText
		buttonSubtitleView.setTextColor(buttonSubtitleTextColor)
	}
	
	private fun initButtonStyles(buttonStyles: ButtonStyles) {
		buttonRoot.background = when (buttonStyles) {
			ButtonStyles.OUTLINED -> {
				ContextCompat.getDrawable(context, R.drawable.buttondp_style_outlined)
			}
			ButtonStyles.GRADIENT -> {
				ContextCompat.getDrawable(context, R.drawable.buttondp_style_gradient)
			}
			ButtonStyles.NORMAL -> {
				ContextCompat.getDrawable(context, R.drawable.buttondp_style_normal)
			}
		}
	}
	
	private fun initButtonIcon() {
		buttonIconView.setImageDrawable(AppCompatResources.getDrawable(context, buttonIconRef))
		buttonIconView.clipToOutline = buttonIconRoundedCorner
	}
	
	private fun initButtonType(buttonType: ButtonTypes) {
		when (buttonType) {
			ButtonTypes.ONLY_TITLE -> {
				buttonSubtitleView.visibility = GONE
				buttonIconView.visibility = GONE
			}
			
			ButtonTypes.WITH_SUBTITLE -> {
				buttonIconView.visibility = GONE
			}
			
			ButtonTypes.WITH_SUBTITLE_AND_ICON -> {
			}
		}
	}
	
	private fun initAttributes(attrs: AttributeSet?, context: Context) {
		attrs?.let {
			val typedArray =
				context.obtainStyledAttributes(it, R.styleable.CustomizableGenericButton)
			try {
				setButtonStylesWithAttrValues(
					typedArray.getInt
						(
						R.styleable.CustomizableGenericButton_button_style,
						0
					)
				)
				setButtonTypeWithAttrValues(
					typedArray.getInt
						(
						R.styleable.CustomizableGenericButton_button_type,
						0
					)
				)
				
				val imageTintColorResource = typedArray.getResourceId(
					R.styleable.CustomizableGenericButton_button_background_tint,
					R.color.buttondp_style_normal
				)
				buttonBackgroundTint = ContextCompat.getColor(context, imageTintColorResource)
				
				//title related attributes
				typedArray.getString(R.styleable.CustomizableGenericButton_button_title_text)
					?.let { string ->
						buttonTitleText = string
					}
				buttonTitleTextColor = typedArray.getColor(
					R.styleable.CustomizableGenericButton_button_title_text_color,
					buttonTitleTextColor
				)
				
				//subtitle related attributes
				typedArray.getString(R.styleable.CustomizableGenericButton_button_subtitle_text)
					?.let { string ->
						buttonSubtitleText = string
					}
				buttonSubtitleTextColor = typedArray.getColor(
					R.styleable.CustomizableGenericButton_button_subtitle_text_color,
					buttonSubtitleTextColor
				)
				
				
				//icon Related attributes
				buttonIconRef = typedArray.getResourceId(
					R.styleable.CustomizableGenericButton_button_icon,
					buttonIconRef
				)
				buttonIconRoundedCorner = typedArray.getBoolean(
					R.styleable.CustomizableGenericButton_button_icon_rounded_corners,
					buttonIconRoundedCorner
				)
				
				
			} finally {
				typedArray.recycle()
			}
			
		}
	}
	
	override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec)
		//TODO
	}
	
	override fun onTouchEvent(event: MotionEvent?): Boolean {
		when (event?.action) {
			MotionEvent.ACTION_DOWN -> onTouch()
			MotionEvent.ACTION_UP -> onClick()
		}
		return super.onTouchEvent(event)
	}
	
	private fun onClick() {
		playClickAnimation()
		onClickListener?.invoke()
	}
	
	private fun onTouch() {
		playTouchAnimation()
	}
	
	private fun playTouchAnimation() {
		//TODO
	}
	
	private fun playClickAnimation() {
		//TODO
	}
	
	private fun setButtonTypeWithAttrValues(buttonTypeAttr: Int) {
		buttonType =
			when (buttonTypeAttr) {
				1 -> ButtonTypes.WITH_SUBTITLE
				
				2 -> ButtonTypes.WITH_SUBTITLE_AND_ICON
				
				else -> ButtonTypes.ONLY_TITLE
			}
	}
	
	private fun setButtonStylesWithAttrValues(buttonStyleAttr: Int) {
		buttonStyles = when (buttonStyleAttr) {
			
			1 -> ButtonStyles.OUTLINED
			
			2 -> ButtonStyles.GRADIENT
			
			else -> ButtonStyles.NORMAL
			
		}
	}
	
	
	enum class ButtonTypes {
		ONLY_TITLE, WITH_SUBTITLE, WITH_SUBTITLE_AND_ICON
	}
	
	enum class ButtonStyles {
		NORMAL, OUTLINED, GRADIENT
	}
}
