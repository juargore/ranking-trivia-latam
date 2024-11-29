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

    override fun getAllTriviaFlags(): List<TriviaFlag> {
        return allFlags
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
        Question(11, I, context.getString(R.string.question_i_11), listOf(BR, AR, UY), context.getString(R.string.info_i_11)),
        Question(12, I, context.getString(R.string.question_i_12), listOf(BR, AR, MX), context.getString(R.string.info_i_12)),
        Question(13, I, context.getString(R.string.question_i_13), listOf(BR, MX, CO), context.getString(R.string.info_i_13)),
        Question(14, I, context.getString(R.string.question_i_14), listOf(CL, MX, GT), context.getString(R.string.info_i_14)),
        Question(15, I, context.getString(R.string.question_i_15), listOf(CO, PE, BR), context.getString(R.string.info_i_15)),
        Question(16, I, context.getString(R.string.question_i_16), listOf(CU, GT, PA), context.getString(R.string.info_i_16)),
        Question(17, I, context.getString(R.string.question_i_17), listOf(BO, PE, MX), context.getString(R.string.info_i_17))
    )

    private val questionsLevelII = listOf(
        Question(21, II, context.getString(R.string.question_ii_21), listOf(BR, PE, BO, VE), context.getString(R.string.info_ii_21)),
        Question(22, II, context.getString(R.string.question_ii_22), listOf(CO, MX, PY, EC), context.getString(R.string.info_ii_22)),
        Question(23, II, context.getString(R.string.question_ii_23), listOf(BR, VE, BO, PY), context.getString(R.string.info_ii_23)),
        Question(24, II, context.getString(R.string.question_ii_24), listOf(MX, PE, BO, CL), context.getString(R.string.info_ii_24)),
        Question(25, II, context.getString(R.string.question_ii_25), listOf(CU, JM, PR, VE), context.getString(R.string.info_ii_25)),
        Question(26, II, context.getString(R.string.question_ii_26), listOf(HN, GT, NI, SV), context.getString(R.string.info_ii_26)),
        Question(27, II, context.getString(R.string.question_ii_27), listOf(GT, MX, BZ, HN), context.getString(R.string.info_ii_27))
    )

    private val questionsLevelIII = listOf(
        Question(31, III, context.getString(R.string.question_iii_31), listOf(BO, SV, PE, CR, PA), context.getString(R.string.info_iii_31)),
        Question(32, III, context.getString(R.string.question_iii_32), listOf(DO, CU, PR, JM, MQ), context.getString(R.string.info_iii_32)),
        Question(33, III, context.getString(R.string.question_iii_33), listOf(MF, HT, PR, SV, JM), context.getString(R.string.info_iii_33)),
        Question(34, III, context.getString(R.string.question_iii_34), listOf(CR, SR, EC, PA, NI), context.getString(R.string.info_iii_34)),
        Question(35, III, context.getString(R.string.question_iii_35), listOf(DO, CU, PR, JM, MQ), context.getString(R.string.info_iii_35)),
        Question(36, III, context.getString(R.string.question_iii_36), listOf(SR, MQ, DO, BZ, JM), context.getString(R.string.info_iii_36)),
        Question(37, III, context.getString(R.string.question_iii_37), listOf(CL, VE, CU, DO, PA), context.getString(R.string.info_iii_37))
    )

    private val questionsLevelIV = listOf(
        Question(41, IV, context.getString(R.string.question_iv_41), listOf(CO, PE, PA, MX, AR, BR), context.getString(R.string.info_iv_41)),
        Question(42, IV, context.getString(R.string.question_iv_42), listOf(AR, CL, CU, MX, UY, CO), context.getString(R.string.info_iv_42)),
        Question(43, IV, context.getString(R.string.question_iv_43), listOf(MX, AR, BR, CL, CU, CO), context.getString(R.string.info_iv_43)),
        Question(44, IV, context.getString(R.string.question_iv_44), listOf(CU, PA, CR, DO, JM, NI), context.getString(R.string.info_iv_44)),
        Question(45, IV, context.getString(R.string.question_iv_45), listOf(BR, AR, MX, UY, CL, PY), context.getString(R.string.info_iv_45)),
        Question(46, IV, context.getString(R.string.question_iv_46), listOf(EC, CR, CO, MX, PE, HN), context.getString(R.string.info_iv_46)),
        Question(47, IV, context.getString(R.string.question_iv_47), listOf(AR, BR, VE, PE, EC, MX), context.getString(R.string.info_iv_47))
    )

    // aquí para abajo puro 6

    private val questionsLevelV = listOf(
        Question(51, V, context.getString(R.string.question_v_51), listOf(BR, MX, AR, CL, PE, CO), context.getString(R.string.info_v_51)),
        Question(52, V, context.getString(R.string.question_v_52), listOf(VE, BR, MX, AR, CO, EC), context.getString(R.string.info_v_52)),
        Question(53, V, context.getString(R.string.question_v_53), listOf(AR, BR, CL, BO, UY, PE), context.getString(R.string.info_v_53)),
        Question(54, V, context.getString(R.string.question_v_54), listOf(BR, AR, MX, CO, CL, PE), context.getString(R.string.info_v_54)),
        Question(55, V, context.getString(R.string.question_v_55), listOf(MX, AR, BR, CL, PE, CO), context.getString(R.string.info_v_55)),
        Question(56, V, context.getString(R.string.question_v_56), listOf(MX, BR, PE, AR, CL, CU), context.getString(R.string.info_v_56)),
        Question(57, V, context.getString(R.string.question_v_57), listOf(UY, CL, AR, CR, BR, MX), context.getString(R.string.info_v_57)),
        Question(58, V, context.getString(R.string.question_v_58), listOf(AR, UY, BR, PE, PY, CL), context.getString(R.string.info_v_58))
    )

    private val questionsLevelVI = listOf(
        Question(61, VI, context.getString(R.string.question_vi_61), listOf(BR, MX, CL, AR, VE, PE), context.getString(R.string.info_vi_61)),
        Question(62, VI, context.getString(R.string.question_vi_62), listOf(CL, BR, AR, MX, PE, CL), context.getString(R.string.info_vi_62)),
        Question(63, VI, context.getString(R.string.question_vi_63), listOf(BR, MX, CO, AR, GT, CU), context.getString(R.string.info_vi_63)),
        Question(64, VI, context.getString(R.string.question_vi_64), listOf(MX, PE, BR, AR, CL, CO), context.getString(R.string.info_vi_64)),
        Question(65, VI, context.getString(R.string.question_vi_65), listOf(BR, VE, PY, AR, CO, CR), context.getString(R.string.info_vi_65)),
        Question(66, VI, context.getString(R.string.question_vi_66), listOf(VE, CL, EC, PA, MX, AR), context.getString(R.string.info_vi_66)),
        Question(67, VI, context.getString(R.string.question_vi_67), listOf(CL, EC, MX, BR, CR, GT), context.getString(R.string.info_vi_67))
    )

    private val questionsLevelVII = listOf(
        Question(71, VII, context.getString(R.string.question_vii_71), listOf(AR, CL, BR, PE, CO, UY), context.getString(R.string.info_vii_71)),
        Question(72, VII, context.getString(R.string.question_vii_72), listOf(DO, CU, PR, CR, PA, JM), context.getString(R.string.info_vii_72)),
        Question(73, VII, context.getString(R.string.question_vii_73), listOf(HN, GT, NI, CR, SV, DO), context.getString(R.string.info_vii_73)),
        Question(74, VII, context.getString(R.string.question_vii_74), listOf(GT, CL, BO, AR, PA, HN), context.getString(R.string.info_vii_74)),
        Question(75, VII, context.getString(R.string.question_vii_75), listOf(MX, AR, BR, CL, CO, PE), context.getString(R.string.info_vii_75)),
        Question(76, VII, context.getString(R.string.question_vii_76), listOf(AR, BR, CL, MX, CO, VE), context.getString(R.string.info_vii_76)),
        Question(77, VII, context.getString(R.string.question_vii_77), listOf(AR, BR, CO, CL, MX, PR), context.getString(R.string.info_vii_77))
    )

    private val questionsLevelVIII = listOf(
        Question(81, VIII, context.getString(R.string.question_viii_81), listOf(AR, BR, CL, VE, MX, CO), context.getString(R.string.info_viii_81)),
        Question(82, VIII, context.getString(R.string.question_viii_82), listOf(AR, MX, BR, CL, UY, PE), context.getString(R.string.info_viii_82)),
        Question(83, VIII, context.getString(R.string.question_viii_83), listOf(AR, BR, MX, CL, UY, CO), context.getString(R.string.info_viii_83)),
        Question(84, VIII, context.getString(R.string.question_viii_84), listOf(DO, GT, CR, PA, SV, HN), context.getString(R.string.info_viii_84)),
        Question(85, VIII, context.getString(R.string.question_viii_85), listOf(AR, CL, BR, MX, UY, PE), context.getString(R.string.info_viii_85)),
        Question(86, VIII, context.getString(R.string.question_viii_86), listOf(AR, BR, CL, MX, UY, CU), context.getString(R.string.info_viii_86)),
        Question(87, VIII, context.getString(R.string.question_viii_87), listOf(MX, AR, BR, CL, CU, CO), context.getString(R.string.info_viii_87))
    )

    private val questionsLevelIX = listOf(
        Question(91, IX, context.getString(R.string.question_ix_91), listOf(BR, AR, CL, MX, UY, CO), context.getString(R.string.info_ix_91)),
        Question(92, IX, context.getString(R.string.question_ix_92), listOf(BR, AR, MX, CL, PE, UY), context.getString(R.string.info_ix_92)),
        Question(93, IX, context.getString(R.string.question_ix_93), listOf(AR, BR, CL, MX, CO, PE), context.getString(R.string.info_ix_93)),
        Question(94, IX, context.getString(R.string.question_ix_94), listOf(AR, BR, CL, MX, CO, PE), context.getString(R.string.info_ix_94)),
        Question(95, IX, context.getString(R.string.question_ix_95), listOf(CO, BR, AR, MX, CL, VE), context.getString(R.string.info_ix_95)),
        Question(96, IX, context.getString(R.string.question_ix_96), listOf(AR, BR, CL, MX, CU, PE), context.getString(R.string.info_ix_96)),
        Question(97, IX, context.getString(R.string.question_ix_97), listOf(AR, BR, MX, UY, CL, PY), context.getString(R.string.info_ix_97)),
        Question(98, IX, context.getString(R.string.question_ix_98), listOf(PA, AR, BO, CL, PE, MX), context.getString(R.string.info_ix_98))
    )

    private val questionsLevelX = listOf(
        Question(101, X, context.getString(R.string.question_x_101), listOf(MX, AR, BR, CL, CO, PE), context.getString(R.string.info_x_101)),
        Question(102, X, context.getString(R.string.question_x_102), listOf(AR, BR, CL, MX, CO, PE), context.getString(R.string.info_x_102)),
        Question(103, X, context.getString(R.string.question_x_103), listOf(CU, NI, HN, GT, PA, CR), context.getString(R.string.info_x_103)),
        Question(104, X, context.getString(R.string.question_x_104), listOf(AR, BR, CL, MX, CO, BO), context.getString(R.string.info_x_104)),
        Question(105, X, context.getString(R.string.question_x_105), listOf(CL, PY, BO, BR, UY, MX), context.getString(R.string.info_x_105)),
        Question(106, X, context.getString(R.string.question_x_106), listOf(AR, BR, MX, CL, CO, UY), context.getString(R.string.info_x_106)),
        Question(107, X, context.getString(R.string.question_x_107), listOf(GT, CU, DO, HN, SV, CR), context.getString(R.string.info_x_107)),
    )

    private val questionsLevelXI = listOf(
        Question(111, XI, context.getString(R.string.question_xi_111), listOf(BR, AR, MX, UY, PY, CO), context.getString(R.string.info_xi_111)),
        Question(112, XI, context.getString(R.string.question_xi_112), listOf(BR, PE, CO, VE, BO, EC), context.getString(R.string.info_xi_112)),
        Question(113, XI, context.getString(R.string.question_xi_113), listOf(CR, CL, UY, AR, PA, MX), context.getString(R.string.info_xi_113)),
        Question(114, XI, context.getString(R.string.question_xi_114), listOf(MX, SV, CR, NI, GT, HN), context.getString(R.string.info_xi_114)),
        Question(115, XI, context.getString(R.string.question_xi_115), listOf(AR, CL, BR, UY, PE, BO), context.getString(R.string.info_xi_115)),
        Question(116, XI, context.getString(R.string.question_xi_116), listOf(BR, AR, MX, PY, CO, VE), context.getString(R.string.info_xi_116)),
        Question(117, XI, context.getString(R.string.question_xi_117), listOf(CL, PE, BR, EC, VE, BO), context.getString(R.string.info_xi_117))
    )

    private val questionsLevelXII = listOf(
        Question(121, XII, context.getString(R.string.question_xii_121), listOf(EC, CR, GT, CO, HN, DO), context.getString(R.string.info_xii_121)),
        Question(122, XII, context.getString(R.string.question_xii_122), listOf(PY, UY, CR, BR, CL, NI), context.getString(R.string.info_xii_122)),
        Question(123, XII, context.getString(R.string.question_xii_123), listOf(BR, AR, MX, UY, CO, CL), context.getString(R.string.info_xii_123)),
        Question(124, XII, context.getString(R.string.question_xii_124), listOf(BR, MX, CO, CL, VE, PE), context.getString(R.string.info_xii_124)),
        Question(125, XII, context.getString(R.string.question_xii_125), listOf(MX, BR, CL, VE, AR, PE), context.getString(R.string.info_xii_125)),
        Question(126, XII, context.getString(R.string.question_xii_126), listOf(BR, CO, VE, AR, CL, PE), context.getString(R.string.info_xii_126)),
        Question(127, XII, context.getString(R.string.question_xii_127), listOf(BR, CO, VE, PE, BO, EC), context.getString(R.string.info_xii_127)),
        Question(128, XII, context.getString(R.string.question_xii_128), listOf(AR, BR, UY, CO, PY, CL), context.getString(R.string.info_xii_128)),
    )

    private val questionsLevelXIII = listOf(
        Question(131, XIII, context.getString(R.string.question_xiii_131), listOf(BR, PA, MX, CL, CO, AR), context.getString(R.string.info_xiii_131)),
        Question(132, XIII, context.getString(R.string.question_xiii_132), listOf(EC, MX, VE, BR, CO, HN), context.getString(R.string.info_xiii_132)),
        Question(133, XIII, context.getString(R.string.question_xiii_133), listOf(CU, UY, CL, AR, CR, PA), context.getString(R.string.info_xiii_133)),
        Question(134, XIII, context.getString(R.string.question_xiii_134), listOf(EC, BR, PE, CO, VE, DO), context.getString(R.string.info_xiii_134)),
        Question(135, XIII, context.getString(R.string.question_xiii_135), listOf(MX, PE, CL, BO, AR, GT), context.getString(R.string.info_xiii_135)),
        Question(136, XIII, context.getString(R.string.question_xiii_136), listOf(CR, HN, GT, SV, NI, PY), context.getString(R.string.info_xiii_136)),
        Question(137, XIII, context.getString(R.string.question_xiii_137), listOf(BR, MX, AR, PY, PE, VE), context.getString(R.string.info_xiii_137)),
        Question(138, XIII, context.getString(R.string.question_xiii_138), listOf(AR, BR, EC, CO, CL, MX), context.getString(R.string.info_xiii_138))
    )

    private val allQuestions = questionsLevelI + questionsLevelII + questionsLevelIII + questionsLevelIV +
            questionsLevelV + questionsLevelVI + questionsLevelVII + questionsLevelVIII + questionsLevelIX +
            questionsLevelX + questionsLevelXI + questionsLevelXII + questionsLevelXIII
}
