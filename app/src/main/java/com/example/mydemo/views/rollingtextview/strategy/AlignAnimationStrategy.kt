package com.example.mydemo.views.rollingtextview.strategy

import com.example.mydemo.views.rollingtextview.TextManager

/**
 * Created by 张宇 on 2019/4/29.
 * E-mail: zhangyu4@yy.com
 * YY: 909017428
 */
@Suppress("MemberVisibilityCanBePrivate")
open class AlignAnimationStrategy(val alignment: TextAlignment) : NormalAnimationStrategy() {

    override fun findCharOrder(
        sourceText: CharSequence,
        targetText: CharSequence,
        index: Int,
        charPool: CharPool
    ): Pair<List<Char>, Direction> {
        val maxLen = Math.max(sourceText.length, targetText.length)
        var srcChar = TextManager.EMPTY
        var tgtChar = TextManager.EMPTY

        val srcRange = getTextRange(sourceText, maxLen)
        val tgtRange = getTextRange(targetText, maxLen)
        if (index in srcRange) {
            srcChar = sourceText[index - srcRange.first]
        }
        if (index in tgtRange) {
            tgtChar = targetText[index - tgtRange.first]
        }

        return findCharOrder(srcChar, tgtChar, index, charPool)
    }

    private fun getTextRange(text: CharSequence, maxLen: Int): IntRange {
        val from: Int = when (alignment) {
            TextAlignment.Left -> 0
            TextAlignment.Center -> Math.round((maxLen - text.length) / 2f)
            TextAlignment.Right -> maxLen - text.length
        }
        val to: Int = from + text.length
        return from until to
    }

    enum class TextAlignment {
        Left, Right, Center
    }
}