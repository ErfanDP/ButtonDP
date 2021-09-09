package me.erfandp.buttondp

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.View.MeasureSpec
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat

class CustomizableGenericButton(
	context: Context,
	attrs: AttributeSet? = null,
	defStyleAttr: Int = 0
): ConstraintLayout(context, attrs, defStyleAttr) {
	
	constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0) {}
	
	private var buttonType = ButtonTypes.ONLY_TITLE
	private var buttonStyles = ButtonStyles.NORMAL
	private var buttonBackgroundTint: Int =
		ContextCompat.getColor(context, R.color.buttondp_style_normal)
	var onTouchListener : (()->Unit)? = null
	
	init {
		View.inflate(context, R.layout.customizable_generic_button_layout, this)
		
		initAttrs(attrs, context)
	}
	
	private fun initAttrs(attrs: AttributeSet?, context: Context) {
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
					R.color.buttondp_style_normal)
				buttonBackgroundTint = ContextCompat.getColor(context, imageTintColorResource)
				
				
				
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
		if(event?.action == MotionEvent.ACTION_UP){
			onTouch()
		}
		return super.onTouchEvent(event)
	}
	
	private fun onTouch() {
		playTouchAnimation()
		onTouchListener?.invoke()
	}
	
	private fun playTouchAnimation(){
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
	
	//TODO clip to outline the icon
	
	enum class ButtonTypes {
		ONLY_TITLE, WITH_SUBTITLE, WITH_SUBTITLE_AND_ICON
	}
	
	enum class ButtonStyles {
		NORMAL, OUTLINED, GRADIENT
	}
}
