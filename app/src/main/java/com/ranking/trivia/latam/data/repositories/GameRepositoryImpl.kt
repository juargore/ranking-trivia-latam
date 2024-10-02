package com.ranking.trivia.latam.data.repositories

import android.content.Context
import com.ranking.trivia.latam.R
import com.ranking.trivia.latam.domain.models.EmptySpace
import com.ranking.trivia.latam.domain.models.FlagId
import com.ranking.trivia.latam.domain.models.FlagId.AR
import com.ranking.trivia.latam.domain.models.FlagId.BZ
import com.ranking.trivia.latam.domain.models.FlagId.BO
import com.ranking.trivia.latam.domain.models.FlagId.BR
import com.ranking.trivia.latam.domain.models.FlagId.CL
import com.ranking.trivia.latam.domain.models.FlagId.CO
import com.ranking.trivia.latam.domain.models.FlagId.CR
import com.ranking.trivia.latam.domain.models.FlagId.CU
import com.ranking.trivia.latam.domain.models.FlagId.EC
import com.ranking.trivia.latam.domain.models.FlagId.SV
import com.ranking.trivia.latam.domain.models.FlagId.GP
import com.ranking.trivia.latam.domain.models.FlagId.GT
import com.ranking.trivia.latam.domain.models.FlagId.GF
import com.ranking.trivia.latam.domain.models.FlagId.GY
import com.ranking.trivia.latam.domain.models.FlagId.HT
import com.ranking.trivia.latam.domain.models.FlagId.HN
import com.ranking.trivia.latam.domain.models.FlagId.MF
import com.ranking.trivia.latam.domain.models.FlagId.MQ
import com.ranking.trivia.latam.domain.models.FlagId.MX
import com.ranking.trivia.latam.domain.models.FlagId.NI
import com.ranking.trivia.latam.domain.models.FlagId.PA
import com.ranking.trivia.latam.domain.models.FlagId.PY
import com.ranking.trivia.latam.domain.models.FlagId.PE
import com.ranking.trivia.latam.domain.models.FlagId.PR
import com.ranking.trivia.latam.domain.models.FlagId.DO
import com.ranking.trivia.latam.domain.models.FlagId.SR
import com.ranking.trivia.latam.domain.models.FlagId.UY
import com.ranking.trivia.latam.domain.models.FlagId.VE
import com.ranking.trivia.latam.domain.models.FlagId.JM
import com.ranking.trivia.latam.domain.models.QuestionLevel.I
import com.ranking.trivia.latam.domain.models.QuestionLevel.II
import com.ranking.trivia.latam.domain.models.QuestionLevel.III
import com.ranking.trivia.latam.domain.models.QuestionLevel.IV
import com.ranking.trivia.latam.domain.models.QuestionLevel.V
import com.ranking.trivia.latam.domain.models.QuestionLevel.VI
import com.ranking.trivia.latam.domain.models.QuestionLevel.VII
import com.ranking.trivia.latam.domain.models.QuestionLevel.VIII
import com.ranking.trivia.latam.domain.models.QuestionLevel.IX
import com.ranking.trivia.latam.domain.models.QuestionLevel.X
import com.ranking.trivia.latam.domain.models.QuestionLevel.XI
import com.ranking.trivia.latam.domain.models.QuestionLevel.XII
import com.ranking.trivia.latam.domain.models.QuestionLevel.XIII
import com.ranking.trivia.latam.domain.models.Question
import com.ranking.trivia.latam.domain.models.QuestionLevel
import com.ranking.trivia.latam.domain.models.TriviaFlag
import com.ranking.trivia.latam.domain.usecases.IGameRepository

class GameRepositoryImpl(context: Context): IGameRepository {

    override fun getNextQuestionLevel(currentLevel: QuestionLevel): QuestionLevel? {
        return when (currentLevel) {
            I -> II
            II -> III
            III -> IV
            IV -> V
            V -> VI
            VI -> VII
            VII -> VIII
            VIII -> IX
            IX -> X
            X -> XI
            XI -> XII
            XII -> XIII
            XIII -> null
        }
    }

    override fun getQuestionById(id: Int): Question {
        return allQuestions.first { it.id == id }
    }

    override fun getQuestionByLevelAndExcludeTheOnesAlreadyPlayed(
        level: QuestionLevel,
        idsAlreadyPlayedByLevel: List<Int>
    ): Question? {
        val questionsToEvaluate = when (level) {
            I -> questionsLevelI
            II -> questionsLevelII
            III -> questionsLevelIII
            IV -> questionsLevelIV
            V -> questionsLevelV
            VI -> questionsLevelVI
            VII -> questionsLevelVII
            VIII -> questionsLevelVIII
            IX -> questionsLevelIX
            X -> questionsLevelX
            XI -> questionsLevelXI
            XII -> questionsLevelXII
            XIII -> questionsLevelXIII
        }

        val availableQuestions: List<Question> = questionsToEvaluate.filter { it.id !in idsAlreadyPlayedByLevel }

        return if (availableQuestions.isEmpty()) {
            null
        } else {
            availableQuestions.random().also {
                it.gameFlags = it.answerFlags.shuffled()
            }
        }
    }

    override fun getTriviaFlagById(flagId: FlagId): TriviaFlag {
        return allFlags.first { it.id == flagId }
    }

    override fun getEmptySpacesByLevel(level: QuestionLevel): List<EmptySpace> {
        val numberOfSpaces = when (level) {
            I -> 3
            II -> 3
            III -> 3
            IV -> 4
            V -> 6
            VI -> 5
            VII -> 4
            VIII -> 3
            IX -> 3
            X -> 4
            XI -> 4
            XII -> 3
            XIII -> 3
        }
        return List(numberOfSpaces) { EmptySpace(it) }
    }

    override fun verifyIfListIsCorrect(userResponse: List<FlagId>, question: Question): Boolean {
        return when (question.level) {
            I -> userResponse == question.answerFlags.take(3)
            II -> userResponse == question.answerFlags.take(3)
            III -> userResponse == question.answerFlags.take(3)
            IV -> userResponse == question.answerFlags.take(4)
            V -> userResponse == question.answerFlags.take(6)
            VI -> userResponse == question.answerFlags.take(5)
            VII -> userResponse == question.answerFlags.take(4)
            VIII -> userResponse == question.answerFlags.take(3)
            IX -> userResponse == question.answerFlags.take(3)
            X -> userResponse == question.answerFlags.take(4)
            XI -> userResponse == question.answerFlags.take(4)
            XII -> userResponse == question.answerFlags.take(3)
            XIII -> userResponse == question.answerFlags.take(3)
        }
    }


    // ======= Constants section ======= //

    private val allFlags = listOf(
        TriviaFlag(AR, context.getString(R.string.country_name_argentina), R.drawable.flag_argentina),
        TriviaFlag(BZ, context.getString(R.string.country_name_belize), R.drawable.flag_belize),
        TriviaFlag(BO, context.getString(R.string.country_name_bolivia), R.drawable.flag_bolivia),
        TriviaFlag(BR, context.getString(R.string.country_name_brasil), R.drawable.flag_brasil),
        TriviaFlag(CL, context.getString(R.string.country_name_chile), R.drawable.flag_chile),
        TriviaFlag(CO, context.getString(R.string.country_name_colombia), R.drawable.flag_colombia),
        TriviaFlag(CR, context.getString(R.string.country_name_costa_rica), R.drawable.flag_costa_rica),
        TriviaFlag(CU, context.getString(R.string.country_name_cuba), R.drawable.flag_cuba),
        TriviaFlag(EC, context.getString(R.string.country_name_ecuador), R.drawable.flag_ecuador),
        TriviaFlag(JM, context.getString(R.string.country_name_jamaica), R.drawable.flag_jamaica),
        TriviaFlag(SV, context.getString(R.string.country_name_el_salvador), R.drawable.flag_el_salvador),
        TriviaFlag(GP, context.getString(R.string.country_name_guadalupe), R.drawable.flag_guadalupe),
        TriviaFlag(GT, context.getString(R.string.country_name_guatemala), R.drawable.flag_guatemala),
        TriviaFlag(GF, context.getString(R.string.country_name_guayana_francesa), R.drawable.flag_guayana_francesa),
        TriviaFlag(GY, context.getString(R.string.country_name_guyana), R.drawable.flag_guyana),
        TriviaFlag(HT, context.getString(R.string.country_name_haiti), R.drawable.flag_haiti),
        TriviaFlag(HN, context.getString(R.string.country_name_honduras), R.drawable.flag_honduras),
        TriviaFlag(MF, context.getString(R.string.country_name_isla_san_martin), R.drawable.flag_isla_san_martin),
        TriviaFlag(MQ, context.getString(R.string.country_name_martinica), R.drawable.flag_martinica),
        TriviaFlag(MX, context.getString(R.string.country_name_mexico), R.drawable.flag_mexico),
        TriviaFlag(NI, context.getString(R.string.country_name_nicaragua), R.drawable.flag_nicaragua),
        TriviaFlag(PA, context.getString(R.string.country_name_panama), R.drawable.flag_panama),
        TriviaFlag(PY, context.getString(R.string.country_name_paraguay), R.drawable.flag_paraguay),
        TriviaFlag(PE, context.getString(R.string.country_name_peru), R.drawable.flag_peru),
        TriviaFlag(PR, context.getString(R.string.country_name_puerto_rico), R.drawable.flag_puerto_rico),
        TriviaFlag(DO, context.getString(R.string.country_name_republica_dominicana), R.drawable.flag_republica_dominicana),
        TriviaFlag(SR, context.getString(R.string.country_name_surinam), R.drawable.flag_surinam),
        TriviaFlag(UY, context.getString(R.string.country_name_uruguay), R.drawable.flag_uruguay),
        TriviaFlag(VE, context.getString(R.string.country_name_venezuela), R.drawable.flag_venezuela)
    )

    private val questionsLevelI = listOf(
        Question(11, I, "¿Qué país ha ganado más MUNDIALES en su historia?", listOf(BR, AR, UY)),
        Question(12, I, "¿Quién tiene mayor EXTENSIÓN territorial?", listOf(BR, AR, MX)),
        Question(13, I, "¿Qué país tiene mayor POBLACIÓN?", listOf(BR, MX, CO)),
        Question(14, I, "¿Quién tiene más VOLCANES ACTIVOS?", listOf(CL, MX, GT)),
        Question(15, I, "¿Quién tiene mayor porcentaje de ENERGÍA RENOVABLE?", listOf(UY, CR, PY)),
        Question(16, I, "¿Quién tiene más sitios declarados PATRIMONIO de la HUMANIDAD en Centroamérica y el Caribe?", listOf(CU, GT, PA)),
        Question(17, I, "¿Quién produce más cacao, EXCLUYENDO a los gigantes como Brasil y Colombia?", listOf(EC, PE, DO))
    )

    private val questionsLevelII = listOf(
        Question(21, II, "¿Quién tiene mayor SUPERFICIE de selva AMAZÓNICA?", listOf(BR, PE, BO, VE)),
        Question(22, II, "¿Qué países tienen la MAYOR tasa de CRÍMENES?", listOf(CO, MX, PY, EC)),
        Question(23, II, "¿Quién tiene los RÍOS más LARGOS de Latinoamérica?", listOf(BR, VE, BO, PY)),
        Question(24, II, "¿Quién produce más PLATA en la región?", listOf(MX, PE, BO, CL)),
        Question(25, II, "¿Quién tiene más medallas olímpicas en ATLETISMO?", listOf(CU, JM, PR, VE)),
        Question(26, II, "¿Quién tiene más hectáreas para el cultivo del CAFÉ en Centroamérica?", listOf(HN, GT, NI, SV)),
        Question(27, II, "¿Quién tiene la mayor cantidad de sitios arqueológicos MAYAS?", listOf(GT, MX, BZ, HN))
    )

    private val questionsLevelIII = listOf(
        Question(31, III, "¿Quién tiene mayor diversidad ÉTNICA en el CARIBE?", listOf(SR, DO, MQ, GY, HT)),
        Question(32, III, "¿Quién tiene mayor producción de RON en el CARIBE?", listOf(DO, CU, PR, JM, MQ)),
        Question(33, III, "¿Quién tiene mayor densidad poblacional (PAÍSES PEQUEÑOS)?", listOf(MF, HT, PR, SV, JM)),
        Question(34, III, "¿Quién tiene mayor porcentaje de ÁREAS PROTEGIDAS en relación con su tamaño?", listOf(CR, SR, EC, PA, NI)),
        Question(35, III, "¿Quién tiene más TURISMO internacional en el caribe?", listOf(DO, CU, PR, JM, MQ)),
        Question(36, III, "¿Quién mayor BIODIVERSIDAD marina en el Caribe y Centroamérica?", listOf(SR, MQ, DO, BZ, JM)),
        Question(37, III, "¿Quién tiene más ISLAS como parte de su territorio?", listOf(CL, VE, CU, DO, PA))
    )

    // TODO: ES EL MISMO QUE EL III
    private val questionsLevelIV = listOf(
        Question(41, IV, "¿Quién tiene mayor diversidad étnica en el Caribe y Sudamérica?", listOf(SR, DO, MQ, GY, HT)),
        Question(42, IV, "¿Quién tiene mayor producción de ron en el Caribe?", listOf(DO, CU, PR, JM, MQ)),
        Question(43, IV, "¿Quién tiene mayor densidad poblacional (países pequeños)?", listOf(MF, HT, PR, SV, JM)),
        Question(44, IV, "¿Quién tiene mayor porcentaje de áreas protegidas en relación con su tamaño?", listOf(CR, SR, EC, PA, NI)),
        Question(45, IV, "¿Quién tiene más turismo internacional en el Caribe?", listOf(DO, CU, PR, JM, MQ)),
        Question(46, IV, "¿Quién mayor biodiversidad marina en el Caribe y Centroamérica?", listOf(SR, MQ, DO, BZ, JM)),
        Question(47, IV, "¿Quién tiene más islas como parte de su territorio?", listOf(CL, VE, CU, DO, PA))
    )

    // aquí para abajo puro 6

    private val questionsLevelV = listOf(
        Question(51, V, "¿Quién tiene la ECONOMÍA más grande según su PIB?", listOf(BR, MX, AR, CL, PE, CO)),
        Question(52, V, "¿Quién tiene la mayor producción de PETRÓLEO?", listOf(VE, BR, MX, AR, CO, EC)),
        Question(53, V, "¿Quién tiene más VOLCANES ACTIVOS?", listOf(CL, MX, GT, NI, EC, CR)), // TODO: SE REPITE
        Question(54, V, "¿Quién tiene mayor diversidad de LENGUAS INDÍGENAS?", listOf(MX, PE, BO, GT, CO, BR)),
        Question(55, V, "¿Quién EXPORTA más frutas tropicales en Latinoamérica?", listOf(EC, CR, CO, MX, PE, HN)),
        Question(56, V, "¿Quién tiene mayor cantidad de Patrimonios de la Humanidad según la UNESCO?", listOf(MX, BR, PE, AR, CL, CU)),
        Question(57, V, "¿Quién tiene mayor cobertura de INTERNET?", listOf(UY, CL, AR, CR, BR, MX)),
        Question(58, V, "¿Qué países han ganado más veces la COPA AMÉRICA?", listOf(AR, UY, BR, PE, PY, CL))
    )

    private val questionsLevelVI = listOf(
        Question(61, VI, "¿Quién tiene las FUERZAS MILITARES más grandes según el número de efectivos?", listOf(BR, MX, CL, AR, VE, PE)),
        Question(62, VI, "¿Quién tiene mayor EXTENSIÓN DE COSTA en Latinoamérica?", listOf(CL, BR, AR, MX, PE, CL)),
        Question(63, VI, "¿Quién produce más AZÚCAR en Latinoamérica?", listOf(BR, MX, CO, AR, GT, CU)),
        Question(64, VI, "¿Quién tiene la mayor cantidad de RESERVAS DE COBRE?", listOf(CL, PE, MX, AR, EC, PA)),
        Question(65, VI, "¿Quién genera más ENERGÍA HIDROELÉCTRICA?", listOf(BR, VE, PY, AR, CO, CR)),
        Question(66, VI, "¿Quién EXPORTA más FRUTAS tropicales?", listOf(EC, CR, CO, MX, PE, HN)),
        Question(67, VI, "¿Quién PRODUCE más FLORES en la región?", listOf(CL, EC, MX, BR, CR, GT))
    )

    private val questionsLevelVII = listOf(
        Question(71, VIII, "¿Qué países han ganado más veces el Premio Ariel a la Mejor Película Iberoamericana?", listOf(AR, CL, BR, PE, CO, UY)),
        Question(72, VII, "¿Qué países reciben más turistas internacionales en Centroamérica y el Caribe?", listOf(DO, CU, PR, CR, PA, JM)),
        Question(73, VII, "¿Qué países tienen la mayor producción de café en Centroamérica y el Caribe?", listOf(HN, GT, NI, CR, SV, DO)),
        Question(74, VII, "¿Qué países tienen mayor extensión de costa en Centroamérica y el Caribe?", listOf(CU, PA, CR, DO, JM, NI)),
        Question(75, VII, "¿Qué países han producido más películas de comedia exitosas en Latinoamérica", listOf(MX, AR, BR, CL, CO, PE)),
        Question(76, VII, "¿Qué países han tenido más películas seleccionadas en el Festival de Cine de Berlín?", listOf(AR, BR, CL, MX, CO, VE)),
        Question(77, VII, "¿Qué países tienen más volcanes activos en Centroamérica y el Caribe?", listOf(GT, NI, SV, CR, HN, PA))
    )

    private val questionsLevelVIII = listOf(
        Question(81, VIII, "¿Qué países han ganado más Premios Goya a la Mejor Película Iberoamericana?", listOf(AR, CL, CU, MX, UY, CO)),
        Question(82, VIII, "¿Qué países han tenido más nominaciones al Óscar en la categoría de Mejor Película Internacional?", listOf(AR, MX, BR, CL, UY, PE)),
        Question(83, VIII, "¿Qué países tienen más festivales de cine internacionales reconocidos?", listOf(AR, BR, MX, CL, UY, CO)),
        Question(84, VIII, "¿Quién tiene las economías más grandes en Centroamérica y el Caribe según su PIB?", listOf(DO, GT, CR, PA, SV, HN)),
        Question(85, VIII, "¿Qué países han ganado más Premios Platino del Cine Iberoamericano?", listOf(AR, CL, BR, MX, UY, PE)),
        Question(86, VIII, "¿Qué países han producido más películas con nominaciones a festivales internacionales?", listOf(AR, BR, CL, MX, UY, CU)),
        Question(87, VIII, "¿Qué países han tenido más actores nominados al Óscar?", listOf(MX, AR, BR, CL, CU, CO))
    )

    private val questionsLevelIX = listOf(
        Question(91, IX, "¿Qué países tienen más producciones animadas reconocidas internacionalmente?", listOf(BR, AR, CL, MX, UY, CO)),
        Question(92, IX, "¿Qué países han tenido más películas seleccionadas en el Festival de Cannes?", listOf(BR, AR, MX, CL, PE, UY)),
        Question(93, IX, "¿Qué países han producido más documentales premiados a nivel internacional?", listOf(AR, BR, CL, MX, CO, PE)),
        Question(94, IX, "¿Qué países tienen más producciones de cine independiente reconocidas", listOf(AR, BR, CL, MX, CO, PE)),
        Question(95, IX, "¿Qué países han sido sede del Festival de Cine de Cartagena?", listOf(CO, BR, AR, MX, CL, VE)),
        Question(96, IX, "¿Qué países tienen más películas basadas en hechos históricos reconocidas internacionalmente?", listOf(AR, BR, CL, MX, CU, PE)),
        Question(97, IX, "¿Qué países han ganado más premios en el Festival de Cine de Mar del Plata?", listOf(AR, BR, MX, UY, CL, PY)),
        Question(98, IX, "¿Qué países han clasificado más veces a la Copa Mundial de la FIFA?", listOf(BR, AR, MX, UY, CL, PY)),
    )

    private val questionsLevelX = listOf(
        Question(101, X, "¿Qué países han producido más películas de cine negro?", listOf(MX, AR, BR, CL, CO, PE)),
        Question(102, X, "¿Qué países han tenido más películas presentadas en el Festival de San Sebastián?", listOf(AR, BR, CL, MX, CO, PE)),
        Question(103, X, "¿Qué países tienen mayor cantidad de manglares en Centroamérica y el Caribe?", listOf(CU, NI, HN, GT, PA, CR)),
        Question(104, X, "¿Qué países han producido más películas sobre conflictos sociales en Latinoamérica?", listOf(AR, BR, CL, MX, CO, BO)),
        Question(105, X, "¿Qué países han tenido más directores galardonados internacionalmente?", listOf(MX, AR, BR, CL, CU, CO)),
        Question(106, X, "¿Qué países tienen más películas de animación infantil reconocidas?", listOf(AR, BR, MX, CL, CO, UY)),
        Question(107, X, "¿Qué países producen más caña de azúcar en Centroamérica y el Caribe?", listOf(GT, CU, DO, HN, SV, CR)),
    )

    private val questionsLevelXI = listOf(
        Question(111, XI, "¿Quién tiene la mayor producción de carne vacuna?", listOf(BR, AR, MX, UY, PY, CO)),
        Question(112, XI, "¿Quién tiene mayor extensión de selva tropical?", listOf(BR, PE, CO, VE, BO, EC)),
        Question(113, XI, "¿Quién tienen los salarios mínimos más altos?", listOf(CR, CL, UY, AR, PA, MX)),
        Question(114, XI, "¿Quién tiene mayor producción de energía geotérmica?", listOf(MX, SV, CR, NI, GT, HN)),
        Question(115, XI, "¿Quién PRODUCE más vino Latinoamérica?", listOf(AR, CL, BR, UY, PE, BO)),
        Question(116, XI, "¿Quién tiene mayor superficie de tierras agrícolas?", listOf(BR, AR, MX, PY, CO, VE)),
        Question(117, XI, "¿Quién tiene más variedad de especies de aves?", listOf(CL, PE, BR, EC, VE, BO))
    )

    private val questionsLevelXII = listOf(
        Question(121, XII, "¿Quién tiene la mayor producción de banano?", listOf(EC, CR, GT, CO, HN, DO)),
        Question(122, XII, "¿Quién genera más electricidad a partir de fuentes renovables?", listOf(PY, UY, CR, BR, CL, NI)),
        Question(123, XII, "¿Quién tienen la mayor producción de productos lácteos?", listOf(BR, AR, MX, UY, CO, CL)),
        Question(124, XII, "¿Quién tienen la mayor biodiversidad marina?", listOf(BR, MX, CO, CL, VE, PE)),
        Question(125, XII, "¿Quién tiene más líneas de metro?", listOf(MX, BR, CL, VE, AR, PE)),
        Question(126, XII, "¿Quién tiene mayor superficie de áreas protegidas?", listOf(BR, CO, VE, AR, CL, PE)),
        Question(127, XII, "¿Quién tiene más selvas tropicales protegidas?", listOf(BR, CO, VE, PE, BO, EC)),
        Question(128, XII, "¿Qué países han tenido más equipos ganadores de la Copa Libertadores?", listOf(AR, BR, UY, CO, PY, CL)),
    )

    private val questionsLevelXIII = listOf(
        Question(131, XIII, "¿Quién tiene los puertos más grandes por volumen de carga?", listOf(BR, PA, MX, CL, CO, AR)),
        Question(132, XIII, "¿Quién tiene la mayor cantidad de producción de camarones?", listOf(EC, MX, VE, BR, CO, HN)),
        Question(133, XIII, "¿Quién tiene las tasas de alfabetización más altas?", listOf(CU, UY, CL, AR, CR, PA)),
        Question(134, XIII, "¿Quién tiene la mayor producción de cacao?", listOf(EC, BR, PE, CO, VE, DO)),
        Question(135, XIII, "¿Quién tiene mayor producción de plata?", listOf(MX, PE, CL, BO, AR, GT)),
        Question(136, XIII, "¿Quién tiene más especies de mamíferos?", listOf(BR, CO, MX, PE, VE, EC)),
        Question(137, XIII, "¿Quién tiene mayor producción de maíz?", listOf(BR, MX, AR, PY, PE, VE)),
        Question(138, XIII, "¿Qué países tienen más títulos de la Copa Sudamericana?", listOf(AR, BR, EC, CO, CL, MX)),
    )

    private val allQuestions = questionsLevelI + questionsLevelII + questionsLevelIII + questionsLevelIV +
            questionsLevelV + questionsLevelVI + questionsLevelVII + questionsLevelVIII + questionsLevelIX +
            questionsLevelX + questionsLevelXI + questionsLevelXII + questionsLevelXIII
}
