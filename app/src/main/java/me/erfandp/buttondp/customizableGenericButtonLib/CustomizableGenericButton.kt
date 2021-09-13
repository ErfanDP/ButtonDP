package me.erfandp.buttondp.customizableGenericButtonLib

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import me.erfandp.buttondp.R
import me.erfandp.buttondp.utils.extentions.getColorCompat
import me.erfandp.buttondp.utils.extentions.getDrawableCompat

class CustomizableGenericButton(
	context: Context,
	attrs: AttributeSet? = null,
	defStyleAttr: Int = 0
): ConstraintLayout(context, attrs, defStyleAttr) {
	constructor(context: Context, attrs: AttributeSet?): this(context, attrs, 0)
	
	//attributes
	private var buttonType = ButtonTypes.ONLY_TITLE
	private var buttonStyles = ButtonStyles.NORMAL
	private var buttonTitleText = context.getString(R.string.button_title)
	private var buttonTitleTextColor = context.getColorCompat(R.color.buttondp_text_normal)
	private var buttonSubtitleText = context.getString(R.string.button_subtitle)
	private var buttonSubtitleTextColor = context.getColorCompat(R.color.buttondp_text_normal)
	
	@DrawableRes
	private var buttonIconRef = R.drawable.erfandp_logo_final_form
	private var buttonIconRoundedCorner = true
	private var buttonBackgroundTint: Int = context.getColorCompat(R.color.buttondp_style_normal)
	private var buttonBackgroundGradientStartColor =
		context.getColorCompat(R.color.buttondp_style_gradient_start)
	private var buttonBackgroundGradientEndColor =
		context.getColorCompat(R.color.buttondp_style_gradient_end)
	
	
	//views initialized in init block
	private val buttonTitleView: TextView
	private val buttonSubtitleView: TextView
	private val buttonIconView: ImageView
	private val buttonRoot: ConstraintLayout
	
	/**
	 * 	high-order function invoked on touch event ACTION.UP
	 */
	var onClickListener: (() -> Unit)? = null
	
	/**
	 * animation called on touch event ACTION.DOWN and End on ACTION.UP
	 *
	 * it's recommended to use animations with repeatCount of ValueAnimator.INFINITE
	 * this animation will keep playing till touch event ACTION.UP is called
	 **/
	var onTouchAnimatorSet = AnimatorSet().also {
		// rotate o degree then 1 degree and so on for one loop of rotation.
		val objectAnimator = ObjectAnimator.ofFloat(this, "rotation", 0f, 1f, 0f, -1f, 0f)
		objectAnimator.repeatCount = ValueAnimator.INFINITE
		objectAnimator.duration = 150
		it.play(objectAnimator)
	}
	
	/**
	 * animation called on touch event ACTION.UP
	 *
	 * it's NOT recommended to  use animations with repeatCount of ValueAnimator.INFINITE
	 **/
	var onCLickAnimatorSet = AnimatorSet().also {
		val objectAnimatorOne = ObjectAnimator.ofFloat(this, "scaleX", 1F, 1.05F, 1F)
		val objectAnimatorTwo = ObjectAnimator.ofFloat(this, "scaleY", 1F, 1.05F, 1F)
		objectAnimatorOne.duration = 300
		objectAnimatorTwo.duration = 300
		it.playTogether(objectAnimatorOne, objectAnimatorTwo)
	}
	
	init {
		val view = View.inflate(context, R.layout.customizable_generic_button_layout, this)
		
		//find and initialize views
		buttonTitleView = view.findViewById(R.id.buttondp_title)
		buttonSubtitleView = view.findViewById(R.id.buttondp_subtitle)
		buttonIconView = view.findViewById(R.id.buttondp_icon)
		buttonRoot = view.findViewById(R.id.buttondp_root)
		
		initAttributes(attrs, context)
		initViews()
		
	}
	
	/**
	 * initialize views based on given attributes
	 */
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
				val drawable = ContextCompat.getDrawable(
					context,
					R.drawable.buttondp_style_outlined
				)
				drawable?.setTint(buttonBackgroundTint)
				drawable
			}
			ButtonStyles.GRADIENT -> {
				val gradientDrawable: GradientDrawable =
					context.getDrawableCompat(R.drawable.buttondp_style_gradient) as GradientDrawable
				gradientDrawable.colors = arrayOf(
					buttonBackgroundGradientStartColor,
					buttonBackgroundGradientEndColor
				).toIntArray()
				gradientDrawable
				
			}
			ButtonStyles.NORMAL -> {
				val drawable = context.getDrawableCompat(R.drawable.buttondp_style_normal)
				drawable?.setTint(buttonBackgroundTint)
				drawable
			}
		}
	}
	
	private fun initButtonIcon() {
		val drawable = context.getDrawableCompat(buttonIconRef)
		if (buttonStyles == ButtonStyles.OUTLINED) {
			drawable?.setTint(buttonBackgroundTint)
		}
		buttonIconView.setImageDrawable(drawable)
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
				
				//Background related attribute
				when (buttonStyles) {
					ButtonStyles.GRADIENT -> {
						val startTintColorResource = typedArray.getResourceId(
							R.styleable.CustomizableGenericButton_button_gradient_start_tint,
							R.color.buttondp_style_gradient_start
						)
						buttonBackgroundGradientStartColor =
							context.getColorCompat(startTintColorResource)
						val endTintColorResource = typedArray.getResourceId(
							R.styleable.CustomizableGenericButton_button_gradient_end_tint,
							R.color.buttondp_style_gradient_end
						)
						buttonBackgroundGradientEndColor =
							context.getColorCompat(endTintColorResource)
					}
					else -> {
						val tintColorResource = typedArray.getResourceId(
							R.styleable.CustomizableGenericButton_button_background_tint,
							R.color.buttondp_style_normal
						)
						buttonBackgroundTint = context.getColorCompat(tintColorResource)
					}
				}
				
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
	
	
	override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
		when (ev?.action) {
			MotionEvent.ACTION_DOWN -> {
				onTouch()
				return true
			}
			MotionEvent.ACTION_UP -> {
				onClick()
			}
		}
		return super.dispatchTouchEvent(ev)
	}
	
	
	private fun onTouch() {
		onTouchAnimatorSet.start()
	}
	
	private fun onClick() {
		onTouchAnimatorSet.end()
		onCLickAnimatorSet.start()
		onClickListener?.invoke()
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
