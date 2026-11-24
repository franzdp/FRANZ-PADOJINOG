package com.example.model

enum class CountObjectKind(val singular: String, val plural: String) {
  HEART("heart", "hearts"),
  MOON("moon", "moons"),
  STAR("star", "stars"),
  SMILEY("smiley face", "smiley faces"),
  BABY("baby", "babies"),
  PARROT("parrot", "parrots"),
  LEAF("leaf", "leaves"),
  FLOWER("flower", "flowers"),
  TREE("tree", "trees"),
  CLOUD("cloud", "clouds"),
  DOG("dog", "dogs"),
  BANANA("banana", "bananas"),
  TRUCK("truck", "trucks"),
  CHICKEN("chicken", "chickens"),
  BEACHBALL("beachball", "beachballs"),
  CARABAO("carabao", "carabaos"),
  DUCK("duck", "ducks"),
  BASKETBALL("basketball", "basketballs"),
  PENCIL("pencil", "pencils"),
  CAMERA("camera", "cameras"),
  MONKEY("monkey", "monkeys"),
  BOOK("book", "books"),
  TOMATO("tomato", "tomatoes"),
  RIBBON("ribbon", "ribbons"),
  FAN("fan", "fans"),
  CRAYON("crayon", "crayons"),
  BALLOON("balloon", "balloons"),
  CUP("cup", "cups"),
  PEN("pen", "pens"),
  WATCH("watch", "watches"),
  CHILD("child", "children"),
  CANDY("candy", "candies"),
  BUTTERFLY("butterfly", "butterflies"),
  TELEPHONE("telephone", "telephones"),
  ERASER("eraser", "erasers"),
  CAKE("cake", "cakes"),
  CAR("car", "cars"),
  GRASSHOPPER("grasshopper", "grasshoppers"),
  TULIP("tulip", "tulips"),
  ICECREAM("ice cream", "ice creams"),
  CAT("cat", "cats"),
  GLASS("glass", "glasses"),
  BOY_SCOUT("boy scout", "boy scouts"),
  TENT("tent", "tents"),
  SNAKE("snake", "snakes"),
  FLASHLIGHT("flashlight", "flashlights"),
  MATCHBOX("matchbox", "matchboxes"),
  JACKET("jacket", "jackets"),
  BAT("bat", "bats"),
  WATER_JUG("water jug", "water jugs"),
  EGG("egg", "eggs"),
  MILK_BOTTLE("bottle of milk", "bottles of milk"),
  BASKET("basket", "baskets"),
  GIRL("girl", "girls"),
  PANCAKE("plate of pancakes", "plates of pancakes"),
  COOKIE("cookie", "cookies"),
  CANE("cane", "canes"),
  WOMAN("woman", "women"),
  MANGO("mango", "mangoes"),
  UMBRELLA("umbrella", "umbrellas"),
  PEAR("pear", "pears"),
  TSHIRT("t-shirt", "t-shirts"),
  DIAMOND("diamond", "diamonds"),
}

enum class CountObjectsMode {
  ENTER_NUMBER,
  WORD_SENTENCE,
  MATCH_GROUPS,
  MATCH_WORD_TO_OBJECT,
}

data class CountCandidateGroup(
  val count: Int,
  val kind: CountObjectKind,
  val label: String = kind.plural,
)

data class CountObjectsProblem(
  override val itemNumber: Int,
  val mode: CountObjectsMode,
  val objectKind: CountObjectKind,
  val count: Int,
  val prompt: String,
  val expectedWords: String = "",
  val wordPrompt: String = "",
  val candidates: List<CountCandidateGroup> = emptyList(),
  val storyContext: String? = null,
  override val hint: String,
) : MathProblem

data class StoryObjectSpec(
  val expectedCount: Int,
  val label: String,
  val kind: CountObjectKind,
)

data class StampObjectsProblem(
  override val itemNumber: Int,
  val prompt: String,
  val objectKind: CountObjectKind,
  val targetCount: Int,
  val storyText: String? = null,
  val storyItems: List<StoryObjectSpec> = emptyList(),
  override val hint: String,
) : MathProblem

enum class SkipGridStyle {
  OBJECT_SEQUENCE,
  NUMBER_GRID_4X5,
  ANIMAL_TRACK,
  COIN_GROUP_TOTAL,
}

data class SkipCountGridProblem(
  override val itemNumber: Int,
  val style: SkipGridStyle,
  val title: String,
  val stepSize: Int = 1,
  val sequenceValues: List<Int?> = emptyList(),
  val expectedBlanks: List<Int> = emptyList(),
  val gridStart: Int = 1,
  val gridGivenValues: Set<Int> = emptySet(),
  val targetBlankValue: Int = 0,
  val coinDenom: Int = 10,
  val coinCount: Int = 0,
  val iconLabel: String = "",
  override val hint: String,
) : MathProblem

enum class OrdinalShapeKind(val label: String) {
  TRIANGLE("Triangle"),
  OVAL("Oval"),
  RECTANGLE("Rectangle"),
  CLOUD("Cloud"),
  DIAMOND("Diamond"),
  SQUARE("Square"),
  SMILEY("Smiley Face"),
  CIRCLE("Circle"),
  HEART("Heart"),
}

data class OrdinalShapeProblem(
  override val itemNumber: Int,
  val shapes: List<OrdinalShapeKind>,
  val isVertical: Boolean,
  val prompt: String,
  val correctIndex: Int,
  override val hint: String,
) : MathProblem

enum class PartitionShapeKind {
  SQUARE,
  CIRCLE,
  RECTANGLE,
  HEXAGON,
  HEART,
}

data class EqualPartsPartitionProblem(
  override val itemNumber: Int,
  val shape: PartitionShapeKind,
  val requiredParts: Int,
  val expectedFractionLabel: String,
  override val hint: String,
) : MathProblem

enum class G2FractionFigureKind {
  CIRCLE_2_VERT,
  CIRCLE_4_ORTHO,
  CIRCLE_4_DIAG,
  TRIANGLE_2_VERT,
  DIAMOND_4_GRID,
  SQUARE_4_GRID,
  SQUARE_4_DIAG,
  HEXAGON_4_GRID,
  HEXAGON_2_VERT,
  RECT_2_DIAG,
  RECT_2_VERT,
  RECT_2_HORIZ,
  RECT_4_STRIP,
  CROSS_2_VERT,
}

enum class InteractiveFractionMode {
  PICK_FRACTION_FROM_FIGURE,
  G2_WRITE_NUMERATOR,
  G2_WRITE_DENOMINATOR,
  SHADE_PARTS,
  DIVIDE_AND_SHADE,
  G3_TRACE_OR_COLOR_CIRCLE,
  G3_HALF_OR_QUARTER_JUDGE,
  G3_BUILD_CIRCLE_PARTS,
}

data class InteractiveFractionProblem(
  override val itemNumber: Int,
  val mode: InteractiveFractionMode,
  val figureKind: G2FractionFigureKind,
  val prompt: String,
  val totalParts: Int,
  val targetShadedParts: Int,
  val preShadedIndices: Set<Int> = emptySet(),
  val fractionOptions: List<String> = emptyList(),
  val correctOption: String = "",
  val allowAnyDivisorFirst: Boolean = false,
  val g3PictureKind: String = "",
  override val hint: String,
) : MathProblem

data class VisualAddSubSentenceProblem(
  override val itemNumber: Int,
  val isAddition: Boolean,
  val objectKind: CountObjectKind,
  val firstNumber: Int,
  val secondNumber: Int,
  val resultNumber: Int,
  val prompt: String,
  override val hint: String,
) : MathProblem

enum class SubSentencePart {
  MINUEND,
  SUBTRAHEND,
  DIFFERENCE,
}

data class EquationPartProblem(
  override val itemNumber: Int,
  val minuend: Int,
  val subtrahend: Int,
  val difference: Int,
  val underlinedPart: SubSentencePart,
  override val hint: String,
) : MathProblem

enum class BasicShapeKind(val label: String) {
  CIRCLE("Circle"),
  SQUARE("Square"),
  RECTANGLE("Rectangle"),
  TRIANGLE("Triangle"),
}

enum class RealWorldObjectKind(val label: String, val shapeKind: BasicShapeKind) {
  COIN("1-Peso Coin", BasicShapeKind.CIRCLE),
  NOTEBOOK("Square Notebook", BasicShapeKind.SQUARE),
  RULER("Ruler", BasicShapeKind.RECTANGLE),
  CORN_CHIP("Corn Chip", BasicShapeKind.TRIANGLE),
  TUNA_CAN("Round Can Top", BasicShapeKind.CIRCLE),
  CHEESE_WEDGE("Cheese Slice", BasicShapeKind.TRIANGLE),
  FLOPPY_DISK("Square Diskette", BasicShapeKind.SQUARE),
  CLOCK("Round Clock", BasicShapeKind.CIRCLE),
  DOOR("Door", BasicShapeKind.RECTANGLE),
  CHALKBOARD("Blackboard", BasicShapeKind.RECTANGLE),
  SUITCASE("Suitcase", BasicShapeKind.RECTANGLE),
  PIZZA_WHOLE("Whole Pizza", BasicShapeKind.CIRCLE),
  PIZZA_SLICE("Pizza Slice", BasicShapeKind.TRIANGLE),
  WATERMELON_SLICE("Watermelon Wedge", BasicShapeKind.TRIANGLE),
  DONUT("Donut", BasicShapeKind.CIRCLE),
  MOON_COOKIE("Round Cookie", BasicShapeKind.CIRCLE),
  SQUARE_CLOCK("Square Wall Clock", BasicShapeKind.SQUARE),
  SQUARE_FRAME("Square Frame", BasicShapeKind.SQUARE),
  HANDKERCHIEF("Handkerchief", BasicShapeKind.SQUARE),
  WINDOW("Square Window", BasicShapeKind.SQUARE),
  MOUNTAIN("Mountain Peak", BasicShapeKind.TRIANGLE),
  TRIANGLE_FLAG("Pennant Flag", BasicShapeKind.TRIANGLE),
  TOWEL("Folded Towel", BasicShapeKind.SQUARE),
  BALL("Basketball", BasicShapeKind.CIRCLE),
}

enum class ShapeExerciseMode {
  NAME_OBJECT_SHAPE,
  PICK_OBJECT_BY_SHAPE,
  PICK_FROM_THREE_SHAPES,
  PICK_FROM_THREE_OBJECTS,
}

data class ShapeClassificationProblem(
  override val itemNumber: Int,
  val mode: ShapeExerciseMode,
  val prompt: String,
  val targetShape: BasicShapeKind,
  val shownObject: RealWorldObjectKind? = null,
  val shapeChoices: List<BasicShapeKind> = emptyList(),
  val objectChoices: List<RealWorldObjectKind> = emptyList(),
  override val hint: String,
) : MathProblem

enum class CalendarMode {
  DAY_BEFORE_AFTER,
  MONTH_ORDINAL,
  DAY_ORDINAL,
  MONTH_BEFORE_AFTER,
  APRIL_CALENDAR_READ,
}

data class CalendarProblem(
  override val itemNumber: Int,
  val mode: CalendarMode,
  val prompt: String,
  val referenceLabel: String,
  val validAnswers: List<String>,
  val displayAnswer: String,
  val quickChoices: List<String> = emptyList(),
  override val hint: String,
) : MathProblem

enum class ExpandedFormMode {
  FIRST_FORMAT,
  SECOND_FORMAT,
  ANY_FORMAT,
}

data class ExpandedFormProblem(
  override val itemNumber: Int,
  val number: Int,
  val promptDisplay: String,
  val mode: ExpandedFormMode,
  override val hint: String,
) : MathProblem

data class CompareWordProblem(
  override val itemNumber: Int,
  val prompt: String,
  val optionA: String,
  val optionB: String,
  val correctOption: String,
  override val hint: String,
) : MathProblem

enum class MultDivExerciseMode {
  PHRASE_OR_REPEAT_TO_MULT,
  NUMBER_LINE_READ,
  NUMBER_LINE_BUILD,
  MULT_PROPERTY_ONLY,
  MULT_PROPERTY_MISSING_VALUE,
  GROUPING_PICTURE_TO_DIV,
  REPEAT_SUB_TO_DIV,
  FACT_TO_DIV_SENTENCE,
  INVERSE_MISSING_FACTOR_AND_QUOTIENT,
  WORD_PHRASE_TO_DIV,
}

data class MultDivSentenceProblem(
  override val itemNumber: Int,
  val mode: MultDivExerciseMode,
  val prompt: String,
  val visualExpression: String,
  val expectedFirst: Int,
  val expectedSecond: Int,
  val expectedResult: Int,
  val expectedPropertyCode: String = "",
  val pictureKind: CountObjectKind? = null,
  val numberLineMax: Int = 15,
  override val hint: String,
) : MathProblem

enum class LineSurfaceMode {
  LINE_STRAIGHT_OR_CURVED,
  SURFACE_FLAT_OR_CURVED_PICK,
  OBJECT_LINE_STRAIGHT_OR_CURVED,
  OBJECT_SURFACE_FLAT_OR_CURVED_WORD,
}

data class LineSurfaceProblem(
  override val itemNumber: Int,
  val mode: LineSurfaceMode,
  val figureId: Int,
  val figureLabel: String,
  val prompt: String,
  val optionA: String,
  val optionB: String,
  val correctOption: String,
  override val hint: String,
) : MathProblem
