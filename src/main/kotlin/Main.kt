package io.github.wbstkr

import processing.core.PApplet
import processing.core.PImage

class App : PApplet() {
    private val tileSize = 8
    private val renderSize = 16f

    private lateinit var spriteSheet: PImage
    private lateinit var tileTypes: List<PImage>

    override fun settings() {
        size(800, 800)
        noSmooth()
        noLoop()
    }

    override fun setup() {
        spriteSheet = loadImage("TileSet.png")
        val cols = spriteSheet.width / tileSize
        val rows = spriteSheet.height / tileSize
        val numOfTiles = rows * cols
        tileTypes = List(numOfTiles) {
            val x = tileSize * (it % cols)
            val y = tileSize * (it / cols)
            spriteSheet.get(x, y, tileSize, tileSize)
        }
    }

    override fun draw() {
        background(0)

        var tiles = Array((width / renderSize).toInt() * (height / renderSize).toInt()) { (0..7).toMutableList() }
        val startingIndex = tiles.indices.random()
        tiles[startingIndex] = mutableListOf(4)
        tiles = waveFunction(
            tiles, mutableListOf(startingIndex), (width / renderSize).toInt(), mutableListOf()
        )

        tiles.forEachIndexed { index, elements ->
            val x = renderSize * (index % floor(width / renderSize))
            val y = renderSize * (index / floor(height / renderSize))
            when (elements.size) {
                0 -> {
                    fill(255f, 0f, 0f)
                    noStroke()
                    square(x, y, renderSize)
                }

                1 -> {
                    image(tileTypes[elements[0]], x, y, renderSize, renderSize)
                }

                else -> {
                    val renderChunkSize = floor(sqrt(elements.size.toFloat()))
                    val offset = renderSize / renderChunkSize
                    var xOff = 0
                    var yOff = 0
                    elements.forEach {
                        image(tileTypes[it], x + xOff, y + yOff, offset, offset)
                        xOff += offset.toInt()
                        if (xOff >= renderSize.toInt()) {
                            xOff = 0
                            yOff += offset.toInt()
                        }
                    }
                }
            }
        }
    }

    override fun keyPressed() {
        if (key == ' ') redraw()
    }

    private fun waveFunction(
        grid: Array<MutableList<Int>>,
        indices: MutableList<Int>,
        rowSize: Int,
        doneList: MutableList<Int>,
    ): Array<MutableList<Int>> {
        val nextBatch = mutableListOf<Int>()
        val potentialBatch = mutableListOf<Int>()
        indices.forEach { index ->
            if (doneList.contains(index)) return@forEach

            val choice = grid[index].random()
            val collapseList = when (choice) {
                0 -> listOf(2, 3, 4, 5, 6, 7)
                1 -> listOf(3, 4, 5, 6, 7)
                2 -> listOf(0, 4, 5, 6, 7)
                3 -> listOf(0, 1, 5, 6, 7)
                4 -> listOf(0, 1, 2, 6, 7)
                5 -> listOf(0, 1, 2, 3, 7)
                6 -> listOf(0, 1, 2, 3, 4)
                7 -> listOf(0, 1, 2, 3, 4, 5)
                else -> listOf()
            }

            val xPos = index % rowSize
            val validNeighbors: MutableList<Int> = mutableListOf()

            if (xPos != 0) validNeighbors.add(index - 1)
            if (xPos != (rowSize - 1)) validNeighbors.add(index + 1)
            if (index >= rowSize) validNeighbors.add(index - rowSize)
            if (index < (grid.size - rowSize)) validNeighbors.add(index + rowSize)

            validNeighbors.forEach {
                grid[it].removeAll(collapseList)
                if (grid[it].isEmpty()) grid[it].add(grid[index].random())
                potentialBatch.add(it)
            }

            grid[index] = mutableListOf(choice)
            doneList.add(index)
        }

        potentialBatch.forEach {
            if (grid[it].size > 1) nextBatch.add(it)
        }

        return if (nextBatch.isEmpty()) grid else waveFunction(grid, nextBatch, rowSize, doneList)
    }

    companion object {
        fun main() {
            main("io.github.wbstkr.${App::class.simpleName}")
        }
    }
}


fun main() {
    App.main()
}