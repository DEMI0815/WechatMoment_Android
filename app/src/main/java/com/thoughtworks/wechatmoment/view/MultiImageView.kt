package com.thoughtworks.wechatmoment.view

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.ImageView.ScaleType
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.thoughtworks.wechatmoment.R

class MultiImageView : LinearLayout {

    private var imagesList: List<String>? = null

    private var oneMaxWandH = 0
    private var moreWandH = 0
    private val imagePadding = 15
    private var countOfPerRow = 3
    private var onePicPara: LayoutParams? = null
    private var morePara: LayoutParams? = null
    private var moreParaColumnFirst: LayoutParams? = null
    private var rowPara: LayoutParams? = null
    private var mOnItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        mOnItemClickListener = onItemClickListener
    }

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    @Throws(IllegalArgumentException::class)
    fun setList(lists: List<String>?) {
        if (lists != null) {
            imagesList = lists
            if (MAX_WIDTH > 0) {
                moreWandH = (MAX_WIDTH - imagePadding * 2) / 3
                oneMaxWandH = MAX_WIDTH * 2 / 3
                initImageLayoutParams()
            }
            initView()
        }
    }

    private fun initImageLayoutParams() {
        val wrap = LayoutParams.WRAP_CONTENT
        val match = LayoutParams.MATCH_PARENT
        onePicPara = LayoutParams(oneMaxWandH, wrap)
        moreParaColumnFirst = LayoutParams(moreWandH, moreWandH)
        morePara = LayoutParams(moreWandH, moreWandH)
        morePara!!.setMargins(imagePadding, 0, 0, 0)
        rowPara = LayoutParams(match, wrap)
    }

    private fun initView() {
        this.orientation = VERTICAL
        removeAllViews()
        if (MAX_WIDTH == 0) {
            addView(View(context))
            return
        }
        if (imagesList!!.size == 1) {
            addView(createImageView(0, false))
        } else {
            val allCount = imagesList!!.size
            countOfPerRow = if (allCount == 4) 2 else 3
            val rowCount =
                (allCount / countOfPerRow + if (allCount % countOfPerRow > 0) 1 else 0) // 行数
            for (rowCursor in 0 until rowCount) {
                val rowLayout = LinearLayout(context)
                rowLayout.orientation = HORIZONTAL
                rowLayout.layoutParams = rowPara
                if (rowCursor != 0) {
                    rowLayout.setPadding(0, imagePadding, 0, 0)
                }
                var columnCount =
                    if (allCount % countOfPerRow == 0) countOfPerRow else allCount % countOfPerRow //每行的列数
                if (rowCursor != rowCount - 1) {
                    columnCount = countOfPerRow
                }
                addView(rowLayout)
                val rowOffset = rowCursor * countOfPerRow // 行偏移
                for (columnCursor in 0 until columnCount) {
                    val position = columnCursor + rowOffset
                    rowLayout.addView(createImageView(position, true))
                }
            }
        }
    }

    private fun createImageView(position: Int, isMultiImage: Boolean): ImageView {
        var url = ""
        if (!TextUtils.isEmpty(imagesList!![position])) {
            url = imagesList!![position]
        }
//        val imageView: ImageView = ColorFilterImageView(context)
        val imageView = ImageView(context)
        if (isMultiImage) {
            imageView.scaleType = ScaleType.CENTER_CROP
            imageView.layoutParams =
                if (position % countOfPerRow == 0) moreParaColumnFirst else morePara
        } else {
            imageView.adjustViewBounds = true
            imageView.scaleType = ScaleType.FIT_START
            imageView.maxHeight = oneMaxWandH
            imageView.layoutParams = onePicPara
        }
        imageView.id = url.hashCode()
        imageView.setOnClickListener {
            if (mOnItemClickListener != null) {
                mOnItemClickListener!!.onItemClick(it, position)
            }
        }
        Glide.with(context).load(url).placeholder(R.drawable.ic_texture).into(imageView)
        return imageView
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (MAX_WIDTH == 0) {
            val width = measureWidth(widthMeasureSpec)
            if (width > 0) {
                MAX_WIDTH = width - paddingLeft - paddingRight
                if (imagesList != null && imagesList!!.isNotEmpty()) {
                    setList(imagesList)
                }
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    private fun measureWidth(measureSpec: Int): Int {
        var result = 0
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize
        } else if (specMode == MeasureSpec.AT_MOST) {
            result = result.coerceAtMost(specSize)
        }
        return result
    }

    interface OnItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }

    //public static int MAX_WIDTH = 0;
    companion object {
        var MAX_WIDTH = 0
    }
}