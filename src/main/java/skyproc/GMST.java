package skyproc;

import lev.LImport;
import skyproc.exceptions.BadParameter;
import skyproc.exceptions.BadRecord;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.zip.DataFormatException;

/**
 * @author Justin Swanson
 */
public class GMST extends MajorRecord {

    // Static prototypes and definitions
    static final SubPrototype GMSTproto = new SubPrototype(MajorRecord.majorProto) {

        @Override
        protected void addRecords() {
            add(new DATA());
        }
    };

    //    public enum USetting {
//	uVideoDeviceIdentifierPart,
//	uiMuteMusicPauseTime;
//    }
    // Common Functions
    GMST() {
        super();
        subRecords.setPrototype(GMSTproto);
    }

    // Enums

    /**
     * @param setting
     * @param b
     */
    public GMST(BoolSetting setting, Boolean b) {
        this();
        originateFromPatch(setting.toString());
        setData(b);
    }

    /**
     * @param setting
     * @param b
     */
    public GMST(String setting, Boolean b) {
        this();
        originateFromPatch(setting);
        setData(b);
    }

    /**
     * @param setting
     * @param s
     */
    public GMST(StringSetting setting, String s) {
        this();
        originateFromPatch(setting.toString());
        setData(s);
    }

    /**
     * @param setting
     * @param s
     */
    public GMST(String setting, String s) {
        this();
        originateFromPatch(setting);
        setData(s);
    }

    /**
     * @param setting
     * @param i
     */
    public GMST(IntSetting setting, int i) {
        this();
        originateFromPatch(setting.toString());
        setData(i);
    }

    /**
     * @param setting
     * @param i
     */
    public GMST(String setting, int i) {
        this();
        originateFromPatch(setting);
        setData(i);
    }

    /**
     * @param setting
     * @param f
     */
    public GMST(FloatSetting setting, float f) {
        this();
        originateFromPatch(setting.toString());
        setData(f);
    }

    /**
     * @param setting
     * @param f
     */
    public GMST(String setting, float f) {
        this();
        originateFromPatch(setting);
        setData(f);
    }

    @Override
    Record getNew() {
        return new GMST();
    }

    @Override
    ArrayList<String> getTypes() {
        return Record.getTypeList("GMST");
    }

    @Override
    void importSubRecords(LImport in) throws BadRecord, DataFormatException, BadParameter {
        SubRecord data = ((SubRecordsStream) subRecords).getSilent("DATA");
        updateDATAtype();
        super.importSubRecords(in);
        ((SubRecordsStream) subRecords).loadFromPosition(data);
    }

    @Override
    void export(ModExporter out) throws IOException {
        updateDATAtype();
        super.export(out);
    }

    @Override
    int getContentLength(boolean isStringTabled) {
        updateDATAtype();
        return super.getContentLength(isStringTabled);
    }

    /**
     * @return The type of data this GMST contains.
     */
    public GMSTType getGMSTType() {
        if (getEDID().length() == 0) {
            return GMSTType.Unknown;
        }
        switch (getEDID().charAt(0)) {
            case 'b':
            case 'B':
                return GMSTType.Bool;
            case 'i':
            case 'I':
                return GMSTType.Int;
            case 'f':
            case 'F':
                return GMSTType.Float;
            case 's':
            case 'S':
                return GMSTType.String;
            default:
                return GMSTType.Unknown;
        }
    }

    DATA getDATA() {
        return (DATA) subRecords.get("DATA");
    }

    /**
     * Sets the data to a boolean value. You must check and be aware that this
     * GMST contains that type.
     *
     * @param b
     */
    final public void setData(Boolean b) {
        if (b) {
            getDATA().DATA.setData(1, 4);
        } else {
            getDATA().DATA.setData(0, 4);
        }
    }

    /**
     * Sets the data to a string value. You must check and be aware that this
     * GMST contains that type.
     *
     * @param s
     */
    final public void setData(String s) {
        getDATA().DATAs.setText(s);
    }

    /**
     * Sets the data to a int value. You must check and be aware that this GMST
     * contains that type.
     *
     * @param i
     */
    final public void setData(int i) {
        getDATA().DATA.setData(i, 4);
    }

    /**
     * Sets the data to a float value. You must check and be aware that this
     * GMST contains that type.
     *
     * @param f
     */
    final public void setData(float f) {
        ByteBuffer out = ByteBuffer.allocate(4);
        out.putInt(Integer.reverseBytes(Float.floatToIntBits(f)));
        getDATA().DATA.setData(out.array());
    }

    // Get/set

    /**
     * @return Returns the value as a bool. You must check and be aware that
     * this GMST contains that type.
     */
    public boolean getBool() {
        return getDATA().DATA.toInt() != 0;
    }

    /**
     * @return Returns the value as a string. You must check and be aware that
     * this GMST contains that type.
     */
    public String getString() {
        return getDATA().DATAs.print();
    }

    /**
     * @return Returns the value as an int. You must check and be aware that
     * this GMST contains that type.
     */
    public int getInt() {
        return getDATA().DATA.toInt();
    }

    /**
     * @return Returns the value as a float. You must check and be aware that
     * this GMST contains that type.
     */
    public float getFloat() {
        return Float.intBitsToFloat(getDATA().DATA.toInt());
    }

    SubRecord updateDATAtype() {
        DATA data = getDATA();
        data.GMSTtype = getGMSTType();
        return data;
    }

    /**
     * Enum representing the different data types a GMST can hold.
     */
    public enum GMSTType {


        Bool,

        Int,

        Float,

        String,

        Unknown
    }


    public enum BoolSetting {


        bAllow,

        bAutoAimBasedOnDistance,

        bCrosshairEnabled,

        bForce,

        bForcePow,

        bFull,

        bRegenNPCMagickaDuringCast,

        bReturnTo,

        bSaveOnPause,

        bSaveOnRest,

        bSaveOnTravel,

        bSaveOnWait,

        bShowInventory,

        bUse,

        bUseWaterHDR,

        bWantCastLeft,

        bWantCastRight,

        bWantCastVoice
    }


    public enum FloatSetting {


        fAIAcquireObjectDistance,

        fAIActivateHeight,

        fAIActivateReach,

        fAIActorPackTargetHeadTrackMod,

        fAIAimBlockedHalfCircleRadius,

        fAIAimBlockedToleranceDegrees,

        fAIAwareofPlayerTimer,

        fAIBestHeadTrackDistance,

        fAICombatFleeScoreThreshold,

        fAICombatNoAreaEffectAllyDistance,

        fAICombatNoTargetLOSPriorityMult,

        fAICombatSlopeDifference,

        fAICombatTargetUnreachablePriorityMult,

        fAICombatUnreachableTargetPriorityMult,

        fAICommentTimeWindow,

        fAIConversationExploreTime,

        fAIDefaultSpeechMult,

        fAIDialogueDistance,

        fAIDistanceRadiusMinLocation,

        fAIDistanceTeammateDrawWeapon,

        fAIDodgeDecisionBase,

        fAIDodgeFavorLeftRightMult,

        fAIDodgeVerticalRangedAttackMult,

        fAIDodgeWalkChance,

        fAIEnergyLevelBase,

        fAIEngergyLevelMult,

        fAIEscortFastTravelMaxDistFromPath,

        fAIEscortHysteresisWidth,

        fAIEscortStartStopDelayTime,

        fAIEscortWaitDistanceExterior,

        fAIEscortWaitDistanceInterior,

        fAIExclusiveGreetingTimer,

        fAIExplosiveWeaponDamageMult,

        fAIExplosiveWeaponRangeMult,

        fAIExteriorSpectatorDetection,

        fAIExteriorSpectatorDistance,

        fAIFaceTargetAnimationAngle,

        fAIFindBedChairsDistance,

        fAIFleeConfBase,

        fAIFleeConfMult,

        fAIFleeHealthMult,

        fAIFleeSuccessTimeout,

        fAIForceGreetingTimer,

        fAIFurnitureDestinationRadius,

        fAIGreetingTimer,

        fAIHeadTrackDialogueOffsetRandomValue,

        fAIHeadTrackDialoguePickNewOffsetTimer,

        fAIHeadTrackDialogueResetPositionTimer,

        fAIHeadTrackDialogueStayInOffsetMax,

        fAIHeadTrackDialogueStayInOffsetMin,

        fAIHeadTrackOffsetRandomValueMax,

        fAIHeadTrackOffsetRandomValueMin,

        fAIHeadTrackPickNewOffsetTimer,

        fAIHeadTrackResetPositionTimer,

        fAIHeadTrackStayInOffsetMax,

        fAIHeadTrackStayInOffsetMin,

        fAIHoldDefaultHeadTrackTimer,

        fAIHorseSearchDistance,

        fAIIdleAnimationDistance,

        fAIIdleAnimationLargeCreatureDistanceMult,

        fAIIdleWaitTime,

        fAIIdleWaitTimeComplexScene,

        fAIInDialogueModeWithPlayerDistance,

        fAIInDialogueModewithPlayerTimer,

        fAIInteriorHeadTrackMult,

        fAIInteriorSpectatorDetection,

        fAIInteriorSpectatorDistance,

        fAILockDoorsSeenRecentlySeconds,

        fAIMagicSpellMult,

        fAIMagicTimer,

        fAIMarkerDestinationRadius,

        fAIMaxAngleRangeMovingToStartSceneDialogue,

        fAIMaxHeadTrackDistance,

        fAIMaxHeadTrackDistanceFromPC,

        fAIMaxLargeCreatureHeadTrackDistance,

        fAIMaxSmileDistance,

        fAIMaxWanderTime,

        fAIMeleeArmorMult,

        fAIMeleeHandMult,

        fAIMinAngleRangeToStartSceneDialogue,

        fAIMinGreetingDistance,

        fAIMinLocationHeight,

        fAIMoveDistanceToRecalcFollowPath,

        fAIMoveDistanceToRecalcTravelPath,

        fAIMoveDistanceToRecalcTravelToActor,

        fAIPatrolHysteresisWidth,

        fAIPatrolMinSecondsAtNormalFurniture,

        fAIPowerAttackCreatureChance,

        fAIPowerAttackKnockdownBonus,

        fAIPowerAttackNPCChance,

        fAIPowerAttackRecoilBonus,

        fAIPursueDistanceLineOfSight,

        fAIRandomizeInitialLocationMinRadius,

        fAIRangMagicSpellMult,

        fAIRangedWeaponMult,

        fAIRevertToScriptTracking,

        fAIShoutMinAimSeconds,

        fAIShoutRetryDelaySeconds,

        fAIShoutToleranceDegrees,

        fAISocialRadiusToTriggerConversation,

        fAISocialRadiusToTriggerConversationInterior,

        fAISocialTimerForConversationsMax,

        fAISocialTimerForConversationsMin,

        fAISocialTimerToWaitForEvent,

        fAISocialchanceForConversation,

        fAISocialchanceForConversationInterior,

        fAISpectatorCommentTimer,

        fAISpectatorRememberThreatTimer,

        fAISpectatorShutdownDistance,

        fAISpectatorThreatDistExplosion,

        fAISpectatorThreatDistMelee,

        fAISpectatorThreatDistMine,

        fAISpectatorThreatDistRanged,

        fAIStayonScriptHeadtrack,

        fAITrespassWarningTimer,

        fAIUpdateMovementRestrictionsDistance,

        fAIUseMagicToleranceDegrees,

        fAIUseWeaponAnimationTimeoutSeconds,

        fAIUseWeaponDistance,

        fAIUseWeaponToleranceDegrees,

        fAIWaitingforScriptCallback,

        fAIWalkAwayTimerForConversation,

        fAIWanderDefaultMinDist,

        fAItalktoNPCtimer,

        fAItalktosameNPCtimer,

        fActionPointsAimAdjustment,

        fActionPointsAttackMagic,

        fActionPointsAttackOneHandMelee,

        fActionPointsAttackRanged,

        fActionPointsAttackTwoHandMelee,

        fActionPointsAttackUnarmed,

        fActionPointsCrouch,

        fActionPointsFOVBase,

        fActionPointsFOVMult,

        fActionPointsHeal,

        fActionPointsPowerAttackMult,

        fActionPointsReload,

        fActionPointsRestoreRate,

        fActionPointsRunAndGunMult,

        fActionPointsStand,

        fActionPointsSwitchWeapon,

        fActionPointsToggleWeaponDrawn,

        fActionPointsVATSMeleeMult,

        fActiveEffectConditionUpdateInterval,

        fActorAlertSoundTimer,

        fActorAlphaFadeSeconds,

        fActorAnimZAdjust,

        fActorArmorDesirabilityDamageMult,

        fActorArmorDesirabilitySkillMult,

        fActorDefaultTurningSpeed,

        fActorLeaveWaterBreathTimer,

        fActorLuckSkillMult,

        fActorStrengthEncumbranceMult,

        fActorSwimBreathBase,

        fActorSwimBreathDamage,

        fActorSwimBreathMult,

        fActorTeleportFadeSeconds,

        fActorWeaponDesirabilityDamageMult,

        fActorWeaponDesirabilitySkillMult,

        fAddictionUsageMonitorThreshold,

        fAiAcquireKillBase,

        fAiAcquireKillMult,

        fAiAcquirePickBase,

        fAiAcquirePickMult,

        fAiAcquireStealBase,

        fAiAcquireStealMult,

        fAlchemyGoldMult,

        fAlchemyIngredientInitMult,

        fAlchemySkillFactor,

        fAmbushOverRideRadiusforPlayerDetection,

        fArmorBaseFactor,

        fArmorRatingBase,

        fArmorRatingMax,

        fArmorRatingPCBase,

        fArmorRatingPCMax,

        fArmorScalingFactor,

        fArmorWeightLightMaxMod,

        fArrowBounceBlockPercentage,

        fArrowBounceLinearSpeed,

        fArrowBounceRotateSpeed,

        fArrowBowFastMult,

        fArrowBowMinTime,

        fArrowBowSlowMult,

        fArrowFakeMass,

        fArrowGravityBase,

        fArrowGravityMin,

        fArrowGravityMult,

        fArrowMaxDistance,

        fArrowMinBowVelocity,

        fArrowMinDistanceForTrails,

        fArrowMinPower,

        fArrowMinSpeedForTrails,

        fArrowMinVelocity,

        fArrowOptimalDistance,

        fArrowSpeedMult,

        fArrowWeakGravity,

        fAuroraFadeInStart,

        fAuroraFadeOutStart,

        fAutoAimMaxDegrees,

        fAutoAimMaxDegreesMelee,

        fAutoAimMaxDegreesVATS,

        fAutoAimMaxDistance,

        fAutoAimScreenPercentage,

        fAutomaticWeaponBurstCooldownTime,

        fAutomaticWeaponBurstFireTime,

        fAutoraFadeIn,

        fAutoraFadeOut,

        fAvoidPlayerDistance,

        fBarterBuyMin,

        fBarterMax,

        fBarterMin,

        fBarterSellMax,

        fBeamWidthDefault,

        fBigBumpSpeed,

        fBleedoutCheck,

        fBleedoutDefault,

        fBleedoutMin,

        fBleedoutRate,

        fBleedoutRecover,

        fBlinkDelayMax,

        fBlinkDelayMin,

        fBlinkDownTime,

        fBlinkUpTime,

        fBlockAmountHandToHandMult,

        fBlockAmountWeaponMult,

        fBlockLevel,

        fBlockMax,

        fBlockPowerAttackMult,

        fBlockScoreNoShieldMult,

        fBlockSkillBase,

        fBlockSkillMult,

        fBlockWeaponBase,

        fBlockWeaponScaling,

        fBloodSplatterCountBase,

        fBloodSplatterCountDamageBase,

        fBloodSplatterCountDamageMult,

        fBloodSplatterCountRandomMargin,

        fBloodSplatterDuration,

        fBloodSplatterFadeStart,

        fBloodSplatterFlareMult,

        fBloodSplatterFlareOffsetScale,

        fBloodSplatterFlareSize,

        fBloodSplatterMaxOpacity,

        fBloodSplatterMaxSize,

        fBloodSplatterMinOpacity,

        fBloodSplatterMinSize,

        fBloodSplatterOpacityChance,

        fBookLight,

        fBowDrawTime,

        fBowHoldTimer,

        fBowNPCSpreadAngle,

        fBowZoomStaminaDrainMult,

        fBowZoomStaminaRegenDelay,

        fBribeBase,

        fBribeCostCurve,

        fBribeMoralityMult,

        fBribeMult,

        fBribeNPCLevelMult,

        fBribeScale,

        fBribeSpeechcraftMult,

        fBumpReactionIdealMoveDist,

        fBumpReactionMinMoveDist,

        fBumpReactionSmallDelayTime,

        fBumpReactionSmallWaitTimer,

        fBuoyancyMultBody,

        fBuoyancyMultExtremity,

        fCameraShakeDistFadeDelta,

        fCameraShakeDistFadeStart,

        fCameraShakeDistMin,

        fCameraShakeExplosionDistMult,

        fCameraShakeFadeTime,

        fCameraShakeMultMin,

        fCameraShakeTime,

        fChaseDetectionTimerSetting,

        fCheckDeadBodyTimer,

        fCheckPositionFallDistance,

        fClosetoPlayerDistance,

        fClothingArmorBase,

        fClothingArmorScale,

        fClothingBase,

        fClothingClassScale,

        fClothingJewelryBase,

        fClothingJewelryScale,

        fCombatAbsoluteMaxRangeMult,

        fCombatAcquirePickupAnimationDelay,

        fCombatAcquireWeaponAmmoMinimumScoreMult,

        fCombatAcquireWeaponAvoidTargetRadius,

        fCombatAcquireWeaponCloseDistanceMax,

        fCombatAcquireWeaponCloseDistanceMin,

        fCombatAcquireWeaponDisarmedAcquireTime,

        fCombatAcquireWeaponDisarmedDistanceMax,

        fCombatAcquireWeaponDisarmedDistanceMin,

        fCombatAcquireWeaponDisarmedTime,

        fCombatAcquireWeaponEnchantmentChargeMult,

        fCombatAcquireWeaponFindAmmoDistance,

        fCombatAcquireWeaponMeleeScoreMult,

        fCombatAcquireWeaponMinimumScoreMult,

        fCombatAcquireWeaponMinimumTargetDistance,

        fCombatAcquireWeaponRangedDistanceMax,

        fCombatAcquireWeaponRangedDistanceMin,

        fCombatAcquireWeaponReachDistance,

        fCombatAcquireWeaponScoreCostMult,

        fCombatAcquireWeaponScoreRatioMax,

        fCombatAcquireWeaponSearchFailedDelay,

        fCombatAcquireWeaponSearchRadiusBuffer,

        fCombatAcquireWeaponSearchSuccessDelay,

        fCombatAcquireWeaponTargetDistanceCheck,

        fCombatAcquireWeaponUnarmedDistanceMax,

        fCombatAcquireWeaponUnarmedDistanceMin,

        fCombatActiveCombatantAttackRangeDistance,

        fCombatActiveCombatantLastSeenTime,

        fCombatAdvanceInnerRadiusMax,

        fCombatAdvanceInnerRadiusMid,

        fCombatAdvanceInnerRadiusMin,

        fCombatAdvanceLastDamagedThreshold,

        fCombatAdvanceNormalAttackChance,

        fCombatAdvanceOuterRadiusMax,

        fCombatAdvanceOuterRadiusMid,

        fCombatAdvanceOuterRadiusMin,

        fCombatAdvancePathRetryTime,

        fCombatAdvanceRadiusStaggerMult,

        fCombatAimDeltaThreshold,

        fCombatAimLastSeenLocationTimeLimit,

        fCombatAimMeleeHighPriorityUpdateTime,

        fCombatAimMeleeUpdateTime,

        fCombatAimProjectileBlockedTime,

        fCombatAimProjectileGroundMinRadius,

        fCombatAimProjectileRandomOffset,

        fCombatAimProjectileUpdateTime,

        fCombatAimTrackTargetUpdateTime,

        fCombatAngleTolerance,

        fCombatAnticipateTime,

        fCombatAnticipatedLocationCheckDistance,

        fCombatApproachTargetSlowdownDecelerationMult,

        fCombatApproachTargetSlowdownDistance,

        fCombatApproachTargetSlowdownUpdateTime,

        fCombatApproachTargetSlowdownVelocityAngle,

        fCombatApproachTargetSprintStopMovingRange,

        fCombatApproachTargetSprintStopRange,

        fCombatApproachTargetUpdateTime,

        fCombatAreaHoldPositionMinimumRadius,

        fCombatAreaStandardAttackedRadius,

        fCombatAreaStandardAttackedTime,

        fCombatAreaStandardCheckViewConeDistanceMax,

        fCombatAreaStandardCheckViewConeDistanceMin,

        fCombatAreaStandardFlyingRadiusMult,

        fCombatAreaStandardRadius,

        fCombatAttackAllowedOverrunDistance,

        fCombatAttackAnimationDrivenDelayTime,

        fCombatAttackAnticipatedDistanceMin,

        fCombatAttackChanceBlockingMultMax,

        fCombatAttackChanceBlockingMultMin,

        fCombatAttackChanceBlockingSwingMult,

        fCombatAttackChanceLastAttackBonus,

        fCombatAttackChanceLastAttackBonusTime,

        fCombatAttackChanceMax,

        fCombatAttackChanceMin,

        fCombatAttackCheckTargetRangeDistance,

        fCombatAttackMovingAttackDistance,

        fCombatAttackMovingAttackReachMult,

        fCombatAttackMovingStrikeAngleMult,

        fCombatAttackPlayerAnticipateMult,

        fCombatAttackStationaryAttackDistance,

        fCombatAttackStrikeAngleMult,

        fCombatAvoidThreatsChance,

        fCombatBackoffChance,

        fCombatBackoffMinDistanceMult,

        fCombatBashChanceMax,

        fCombatBashChanceMin,

        fCombatBashReach,

        fCombatBashTargetBlockingMult,

        fCombatBetweenAdvanceTimer,

        fCombatBlockAttackChanceMax,

        fCombatBlockAttackChanceMin,

        fCombatBlockAttackReachMult,

        fCombatBlockAttackStrikeAngleMult,

        fCombatBlockChanceMax,

        fCombatBlockChanceMin,

        fCombatBlockChanceWeaponMult,

        fCombatBlockMaxTargetRetreatVelocity,

        fCombatBlockStartDistanceMax,

        fCombatBlockStartDistanceMin,

        fCombatBlockStopDistanceMax,

        fCombatBlockStopDistanceMin,

        fCombatBlockTimeMax,

        fCombatBlockTimeMid,

        fCombatBlockTimeMin,

        fCombatBoundWeaponDPSBonus,

        fCombatBuffMaxTimer,

        fCombatBuffStandoffTimer,

        fCombatCastConcentrationOffensiveMagicCastTimeMax,

        fCombatCastConcentrationOffensiveMagicCastTimeMin,

        fCombatCastConcentrationOffensiveMagicChanceMax,

        fCombatCastConcentrationOffensiveMagicChanceMin,

        fCombatCastConcentrationOffensiveMagicWaitTimeMax,

        fCombatCastConcentrationOffensiveMagicWaitTimeMin,

        fCombatCastImmediateOffensiveMagicChanceMax,

        fCombatCastImmediateOffensiveMagicChanceMin,

        fCombatCastImmediateOffensiveMagicHoldTimeAbsoluteMin,

        fCombatCastImmediateOffensiveMagicHoldTimeMax,

        fCombatCastImmediateOffensiveMagicHoldTimeMin,

        fCombatCastImmediateOffensiveMagicHoldTimeMinDistance,

        fCombatChangeProcessFaceTargetDistance,

        fCombatCircleAngleMax,

        fCombatCircleAngleMin,

        fCombatCircleAnglePlayerMult,

        fCombatCircleChanceMax,

        fCombatCircleChanceMin,

        fCombatCircleDistanceMax,

        fCombatCircleDistantChanceMax,

        fCombatCircleDistantChanceMin,

        fCombatCircleMinDistanceMult,

        fCombatCircleMinDistanceRadiusMult,

        fCombatCircleMinMovementDistance,

        fCombatCircleViewConeAngle,

        fCombatCloseRangeTrackTargetDistance,

        fCombatClusterUpdateTime,

        fCombatCollectAlliesTimer,

        fCombatConfidenceModifierMax,

        fCombatConfidenceModifierMin,

        fCombatCoverAttackMaxWaitTime,

        fCombatCoverAttackOffsetDistance,

        fCombatCoverAttackTimeMax,

        fCombatCoverAttackTimeMid,

        fCombatCoverAttackTimeMin,

        fCombatCoverAvoidTargetRadius,

        fCombatCoverCheckCoverHeightMin,

        fCombatCoverCheckCoverHeightOffset,

        fCombatCoverEdgeOffsetDistance,

        fCombatCoverLedgeOffsetDistance,

        fCombatCoverMaxRangeMult,

        fCombatCoverMidPointMaxRangeBuffer,

        fCombatCoverMinimumActiveRange,

        fCombatCoverMinimumRange,

        fCombatCoverObstacleMovedTime,

        fCombatCoverRangeMaxActiveMult,

        fCombatCoverRangeMaxBufferDistance,

        fCombatCoverRangeMinActiveMult,

        fCombatCoverRangeMinBufferDistance,

        fCombatCoverReservationWidthMult,

        fCombatCoverSearchDistanceMax,

        fCombatCoverSearchDistanceMin,

        fCombatCoverSearchFailedDelay,

        fCombatCoverSecondaryThreatLastSeenTime,

        fCombatCoverSecondaryThreatMinDistance,

        fCombatCoverWaitLookOffsetDistance,

        fCombatCoverWaitTimeMax,

        fCombatCoverWaitTimeMid,

        fCombatCoverWaitTimeMin,

        fCombatCurrentWeaponAbsoluteMaxRangeMult,

        fCombatDPSBowSpeedMult,

        fCombatDPSMeleeSpeedMult,

        fCombatDamageBonusMeleeSneakingMult,

        fCombatDamageBonusSneakingMult,

        fCombatDamageScale,

        fCombatDeadActorHitConeMult,

        fCombatDetectionDialogueIdleMaxElapsedTime,

        fCombatDetectionDialogueIdleMinElapsedTime,

        fCombatDetectionDialogueMaxElapsedTime,

        fCombatDetectionDialogueMinElapsedTime,

        fCombatDetectionFleeingLostRemoveTime,

        fCombatDetectionLostCheckNoticedDistance,

        fCombatDetectionLostRemoveDistance,

        fCombatDetectionLostRemoveDistanceTime,

        fCombatDetectionLostRemoveTime,

        fCombatDetectionLostTimeLimit,

        fCombatDetectionLowDetectionDistance,

        fCombatDetectionLowPriorityDistance,

        fCombatDetectionNoticedDistanceLimit,

        fCombatDetectionNoticedTimeLimit,

        fCombatDetectionVeryLowPriorityDistance,

        fCombatDialogueAllyKilledDistanceMult,

        fCombatDialogueAllyKilledMaxElapsedTime,

        fCombatDialogueAllyKilledMinElapsedTime,

        fCombatDialogueAttackDistanceMult,

        fCombatDialogueAttackMaxElapsedTime,

        fCombatDialogueAttackMinElapsedTime,

        fCombatDialogueAvoidThreatDistanceMult,

        fCombatDialogueAvoidThreatMaxElapsedTime,

        fCombatDialogueAvoidThreatMinElapsedTime,

        fCombatDialogueBashDistanceMult,

        fCombatDialogueBleedOutMaxElapsedTime,

        fCombatDialogueBleedOutMinElapsedTime,

        fCombatDialogueBleedoutDistanceMult,

        fCombatDialogueBlockDistanceMult,

        fCombatDialogueDeathDistanceMult,

        fCombatDialogueFleeDistanceMult,

        fCombatDialogueFleeMaxElapsedTime,

        fCombatDialogueFleeMinElapsedTime,

        fCombatDialogueGroupStrategyDistanceMult,

        fCombatDialogueHitDistanceMult,

        fCombatDialoguePowerAttackDistanceMult,

        fCombatDialogueTauntDistanceMult,

        fCombatDialogueTauntMaxElapsedTime,

        fCombatDialogueTauntMinElapsedTime,

        fCombatDisarmedFindBetterWeaponInitialTime,

        fCombatDisarmedFindBetterWeaponTime,

        fCombatDismemberedLimbVelocity,

        fCombatDistance,

        fCombatDistanceMin,

        fCombatDiveBombChanceMax,

        fCombatDiveBombChanceMin,

        fCombatDiveBombOffsetPercent,

        fCombatDiveBombSlowDownDistance,

        fCombatDodgeAccelerationMult,

        fCombatDodgeAcceptableThreatScoreMult,

        fCombatDodgeAnticipateThreatTime,

        fCombatDodgeBufferDistance,

        fCombatDodgeChanceMax,

        fCombatDodgeChanceMin,

        fCombatDodgeDecelerationMult,

        fCombatDodgeMovingReactionTime,

        fCombatDodgeReactionTime,

        fCombatEffectiveDistanceAnticipateTime,

        fCombatEnvironmentBloodChance,

        fCombatFallbackChanceMax,

        fCombatFallbackChanceMin,

        fCombatFallbackDistanceMax,

        fCombatFallbackDistanceMin,

        fCombatFallbackMaxAngle,

        fCombatFallbackMinMovementDistance,

        fCombatFallbackWaitTimeMax,

        fCombatFallbackWaitTimeMin,

        fCombatFindAllyAttackLocationAllyRadius,

        fCombatFindAllyAttackLocationDistanceMax,

        fCombatFindAllyAttackLocationDistanceMin,

        fCombatFindAttackLocationAvoidTargetRadius,

        fCombatFindAttackLocationDistance,

        fCombatFindAttackLocationKeyAngle,

        fCombatFindAttackLocationKeyHeight,

        fCombatFindBetterWeaponTime,

        fCombatFindLateralAttackLocationDistance,

        fCombatFindLateralAttackLocationIntervalMax,

        fCombatFindLateralAttackLocationIntervalMin,

        fCombatFiringArcStationaryTurnMult,

        fCombatFlankingAngleOffset,

        fCombatFlankingAngleOffsetCostMult,

        fCombatFlankingAngleOffsetMax,

        fCombatFlankingDirectionDistanceMult,

        fCombatFlankingDirectionGoalAngleOffset,

        fCombatFlankingDirectionOffsetCostMult,

        fCombatFlankingDirectionRotateAngleOffset,

        fCombatFlankingDistanceMax,

        fCombatFlankingDistanceMin,

        fCombatFlankingGoalAngleFarMax,

        fCombatFlankingGoalAngleFarMaxDistance,

        fCombatFlankingGoalAngleFarMin,

        fCombatFlankingGoalAngleFarMinDistance,

        fCombatFlankingGoalAngleNear,

        fCombatFlankingGoalCheckDistanceMax,

        fCombatFlankingGoalCheckDistanceMin,

        fCombatFlankingGoalCheckDistanceMult,

        fCombatFlankingLocationGridSize,

        fCombatFlankingMaxTurnAngle,

        fCombatFlankingMaxTurnAngleGoal,

        fCombatFlankingNearDistance,

        fCombatFlankingRotateAngle,

        fCombatFlankingStalkRange,

        fCombatFlankingStalkTimeMax,

        fCombatFlankingStalkTimeMin,

        fCombatFlankingStepDistanceMax,

        fCombatFlankingStepDistanceMin,

        fCombatFlankingStepDistanceMult,

        fCombatFleeAllyDistanceMax,

        fCombatFleeAllyDistanceMin,

        fCombatFleeAllyRadius,

        fCombatFleeCoverMinDistance,

        fCombatFleeCoverSearchRadius,

        fCombatFleeDistanceExterior,

        fCombatFleeDistanceInterior,

        fCombatFleeDoorDistanceMax,

        fCombatFleeDoorTargetCheckDistance,

        fCombatFleeInitialDoorRestrictChance,

        fCombatFleeLastDoorRestrictTime,

        fCombatFleeTargetAvoidRadius,

        fCombatFleeTargetGatherRadius,

        fCombatFleeUseDoorChance,

        fCombatFleeUseDoorRestrictTime,

        fCombatFlightEffectiveDistance,

        fCombatFlightMinimumRange,

        fCombatFlyingAttackChanceMax,

        fCombatFlyingAttackChanceMin,

        fCombatFlyingAttackTargetDistanceThreshold,

        fCombatFollowRadiusBase,

        fCombatFollowRadiusMin,

        fCombatFollowRadiusMult,

        fCombatFollowSneakFollowRadius,

        fCombatForwardAttackChance,

        fCombatGiantCreatureReachMult,

        fCombatGrenadeBounceTimeMax,

        fCombatGrenadeBounceTimeMin,

        fCombatGroundAttackChanceMax,

        fCombatGroundAttackChanceMin,

        fCombatGroundAttackTimeMax,

        fCombatGroundAttackTimeMin,

        fCombatGroupCombatStrengthUpdateTime,

        fCombatGroupOffensiveMultMin,

        fCombatGuardFollowBufferDistance,

        fCombatGuardRadiusMin,

        fCombatGuardRadiusMult,

        fCombatHealthRegenRateMult,

        fCombatHideCheckViewConeDistanceMax,

        fCombatHideCheckViewConeDistanceMin,

        fCombatHideFailedTargetDistance,

        fCombatHideFailedTargetLOSDistance,

        fCombatHitConeAngle,

        fCombatHoverAngleLimit,

        fCombatHoverAngleMax,

        fCombatHoverAngleMin,

        fCombatHoverChanceMax,

        fCombatHoverChanceMin,

        fCombatHoverTimeMax,

        fCombatHoverTimeMin,

        fCombatInTheWayTimer,

        fCombatInventoryDesiredRangeScoreMultMax,

        fCombatInventoryDesiredRangeScoreMultMid,

        fCombatInventoryDesiredRangeScoreMultMin,

        fCombatInventoryDualWieldScorePenalty,

        fCombatInventoryEquipmentMinScoreMult,

        fCombatInventoryEquippedScoreBonus,

        fCombatInventoryMaxRangeEquippedBonus,

        fCombatInventoryMaxRangeScoreMult,

        fCombatInventoryMeleeEquipRange,

        fCombatInventoryMinEquipTimeBlock,

        fCombatInventoryMinEquipTimeDefault,

        fCombatInventoryMinEquipTimeMagic,

        fCombatInventoryMinEquipTimeShout,

        fCombatInventoryMinEquipTimeStaff,

        fCombatInventoryMinEquipTimeTorch,

        fCombatInventoryMinEquipTimeWeapon,

        fCombatInventoryMinRangeScoreMult,

        fCombatInventoryMinRangeUnequippedBonus,

        fCombatInventoryOptimalRangePercent,

        fCombatInventoryRangedScoreMult,

        fCombatInventoryResourceCurrentRequiredMult,

        fCombatInventoryResourceDesiredRequiredMult,

        fCombatInventoryResourceRegenTime,

        fCombatInventoryShieldEquipRange,

        fCombatInventoryShoutMaxRecoveryTime,

        fCombatInventoryTorchEquipRange,

        fCombatInventoryUpdateTimer,

        fCombatIronSightsDistance,

        fCombatIronSightsRangeMult,

        fCombatItemBuffTimer,

        fCombatItemRestoreTimer,

        fCombatKillMoveDamageMult,

        fCombatLandingAvoidActorRadius,

        fCombatLandingSearchDistance,

        fCombatLandingZoneDistance,

        fCombatLineOfSightTimer,

        fCombatLocationTargetRadiusMin,

        fCombatLowFleeingTargetHitPercent,

        fCombatLowMaxAttackDistance,

        fCombatLowTargetHitPercent,

        fCombatMagicArmorDistanceMax,

        fCombatMagicArmorDistanceMin,

        fCombatMagicArmorMinCastTime,

        fCombatMagicBoundItemDistance,

        fCombatMagicBuffDuration,

        fCombatMagicCloakDistanceMax,

        fCombatMagicCloakDistanceMin,

        fCombatMagicCloakMinCastTime,

        fCombatMagicConcentrationAimVariance,

        fCombatMagicConcentrationFiringArcMult,

        fCombatMagicConcentrationMinCastTime,

        fCombatMagicConcentrationScoreDuration,

        fCombatMagicDefaultLongDuration,

        fCombatMagicDefaultMinCastTime,

        fCombatMagicDefaultShortDuration,

        fCombatMagicDisarmDistance,

        fCombatMagicDisarmRestrictTime,

        fCombatMagicDrinkPotionWaitTime,

        fCombatMagicDualCastChance,

        fCombatMagicDualCastInterruptTime,

        fCombatMagicImmediateAimVariance,

        fCombatMagicInvisibilityDistance,

        fCombatMagicInvisibilityMinCastTime,

        fCombatMagicLightMinCastTime,

        fCombatMagicOffensiveMinCastTime,

        fCombatMagicParalyzeDistance,

        fCombatMagicParalyzeMinCastTime,

        fCombatMagicParalyzeRestrictTime,

        fCombatMagicProjectileFiringArc,

        fCombatMagicReanimateDistance,

        fCombatMagicReanimateMinCastTime,

        fCombatMagicReanimateRestrictTime,

        fCombatMagicStaggerDistance,

        fCombatMagicSummonMinCastTime,

        fCombatMagicSummonRestrictTime,

        fCombatMagicTacticalDuration,

        fCombatMagicTargetEffectMinCastTime,

        fCombatMagicWardAttackRangeDistance,

        fCombatMagicWardAttackReachMult,

        fCombatMagicWardCooldownTime,

        fCombatMagicWardMagickaCastLimit,

        fCombatMagicWardMagickaEquipLimit,

        fCombatMagicWardMinCastTime,

        fCombatMagickaRegenRateMult,

        fCombatMaintainOptimalDistanceMaxAngle,

        fCombatMaintainRangeDistanceMin,

        fCombatMaxHoldScore,

        fCombatMaximumOptimalRangeMax,

        fCombatMaximumOptimalRangeMid,

        fCombatMaximumOptimalRangeMin,

        fCombatMaximumProjectileRange,

        fCombatMaximumRange,

        fCombatMeleeTrackTargetDistanceMax,

        fCombatMeleeTrackTargetDistanceMin,

        fCombatMinEngageDistance,

        fCombatMissileImpaleDepth,

        fCombatMissileStickDepth,

        fCombatMonitorBuffsTimer,

        fCombatMoveToActorBufferDistance,

        fCombatMusicGroupThreatRatioMax,

        fCombatMusicGroupThreatRatioMin,

        fCombatMusicGroupThreatRatioTimer,

        fCombatMusicNearCombatInnerRadius,

        fCombatMusicNearCombatOuterRadius,

        fCombatMusicPlayerCombatStrengthCap,

        fCombatMusicPlayerNearStrengthMult,

        fCombatMusicPlayerTargetedThreatRatio,

        fCombatMusicStopTime,

        fCombatMusicUpdateTime,

        fCombatOffensiveBashChanceMax,

        fCombatOffensiveBashChanceMin,

        fCombatOptimalRangeMaxBufferDistance,

        fCombatOptimalRangeMinBufferDistance,

        fCombatOrbitDistance,

        fCombatOrbitTimeMax,

        fCombatOrbitTimeMin,

        fCombatParalyzeTacticalDuration,

        fCombatPathingAccelerationMult,

        fCombatPathingCurvedPathSmoothingMult,

        fCombatPathingDecelerationMult,

        fCombatPathingGoalRayCastPathDistance,

        fCombatPathingIncompletePathMinDistance,

        fCombatPathingLocationCenterOffsetMult,

        fCombatPathingLookAheadDelta,

        fCombatPathingNormalizedRotationSpeed,

        fCombatPathingRefLocationUpdateDistance,

        fCombatPathingRefLocationUpdateTimeDistanceMax,

        fCombatPathingRefLocationUpdateTimeDistanceMin,

        fCombatPathingRefLocationUpdateTimeMax,

        fCombatPathingRefLocationUpdateTimeMin,

        fCombatPathingRetryWaitTime,

        fCombatPathingRotationAccelerationMult,

        fCombatPathingStartRayCastPathDistance,

        fCombatPathingStraightPathCheckDistance,

        fCombatPathingStraightRayCastPathDistance,

        fCombatPathingUpdatePathCostMult,

        fCombatPerchAttackChanceMax,

        fCombatPerchAttackChanceMin,

        fCombatPerchAttackTimeMax,

        fCombatPerchAttackTimeMin,

        fCombatPerchMaxTargetAngle,

        fCombatPlayerBleedoutHealthDamageMult,

        fCombatPlayerLimbDamageMult,

        fCombatProjectileMaxRangeMult,

        fCombatProjectileMaxRangeOptimalMult,

        fCombatRadiusMinMult,

        fCombatRangedAimVariance,

        fCombatRangedAttackChanceLastAttackBonus,

        fCombatRangedAttackChanceLastAttackBonusTime,

        fCombatRangedAttackChanceMax,

        fCombatRangedAttackChanceMin,

        fCombatRangedAttackHoldTimeAbsoluteMin,

        fCombatRangedAttackHoldTimeMax,

        fCombatRangedAttackHoldTimeMin,

        fCombatRangedAttackHoldTimeMinDistance,

        fCombatRangedAttackMaximumHoldTime,

        fCombatRangedDistance,

        fCombatRangedMinimumRange,

        fCombatRangedProjectileFiringArc,

        fCombatRangedStandoffTimer,

        fCombatRelativeDamageMod,

        fCombatRestoreHealthPercentMax,

        fCombatRestoreHealthPercentMin,

        fCombatRestoreHealthRestrictTime,

        fCombatRestoreMagickaPercentMax,

        fCombatRestoreMagickaPercentMin,

        fCombatRestoreMagickaRestrictTime,

        fCombatRestoreStopCastThreshold,

        fCombatRoundAmount,

        fCombatSearchAreaUpdateTime,

        fCombatSearchCenterRadius,

        fCombatSearchCheckDestinationDistanceMax,

        fCombatSearchCheckDestinationDistanceMid,

        fCombatSearchCheckDestinationDistanceMin,

        fCombatSearchCheckDestinationTime,

        fCombatSearchDoorDistance,

        fCombatSearchDoorDistanceLow,

        fCombatSearchDoorSearchRadius,

        fCombatSearchExteriorRadiusMax,

        fCombatSearchExteriorRadiusMin,

        fCombatSearchIgnoreLocationRadius,

        fCombatSearchInteriorRadiusMax,

        fCombatSearchInteriorRadiusMin,

        fCombatSearchInvestigateTime,

        fCombatSearchLocationCheckDistance,

        fCombatSearchLocationCheckTime,

        fCombatSearchLocationInitialCheckTime,

        fCombatSearchLocationInvestigateDistance,

        fCombatSearchLocationRadius,

        fCombatSearchLookTime,

        fCombatSearchRadiusBufferDistance,

        fCombatSearchRadiusMemberDistance,

        fCombatSearchSightRadius,

        fCombatSearchStartWaitTime,

        fCombatSearchUpdateTime,

        fCombatSearchWanderDistance,

        fCombatSelectTargetSwitchUpdateTime,

        fCombatSelectTargetUpdateTime,

        fCombatShoutHeadTrackingAngleMovingMult,

        fCombatShoutHeadTrackingAngleMult,

        fCombatShoutLongRecoveryTime,

        fCombatShoutMaxHeadTrackingAngle,

        fCombatShoutReleaseTime,

        fCombatShoutShortRecoveryTime,

        fCombatSneakAttackBonusMult,

        fCombatSpeakAttackChance,

        fCombatSpeakHitChance,

        fCombatSpeakHitThreshold,

        fCombatSpeakPowerAttackChance,

        fCombatSpeakTauntChance,

        fCombatSpecialAttackChanceMax,

        fCombatSpecialAttackChanceMin,

        fCombatSpeedValueFastWalk,

        fCombatSpeedValueJog,

        fCombatSpeedValueRun,

        fCombatSpeedValueWalk,

        fCombatSplashDamageMaxSpeed,

        fCombatSplashDamageMinDamage,

        fCombatSplashDamageMinRadius,

        fCombatStaffTimer,

        fCombatStaminaRegenRateMult,

        fCombatStealthPointAttackedMaxValue,

        fCombatStealthPointDetectedEventMaxValue,

        fCombatStealthPointDrainMult,

        fCombatStealthPointMax,

        fCombatStealthPointRegenAlertWaitTime,

        fCombatStealthPointRegenAttackedWaitTime,

        fCombatStealthPointRegenDetectedEventWaitTime,

        fCombatStealthPointRegenLostWaitTime,

        fCombatStealthPointRegenMin,

        fCombatStealthPointRegenMult,

        fCombatStepAdvanceDistance,

        fCombatStrafeChanceMax,

        fCombatStrafeChanceMin,

        fCombatStrafeDistanceMax,

        fCombatStrafeDistanceMin,

        fCombatStrafeMinDistanceRadiusMult,

        fCombatStrengthUpdateTime,

        fCombatSurroundDistanceMax,

        fCombatSurroundDistanceMin,

        fCombatTargetEngagedLastSeenTime,

        fCombatTargetLocationAvoidNodeRadiusOffset,

        fCombatTargetLocationCurrentReservationDistanceMult,

        fCombatTargetLocationMaxDistance,

        fCombatTargetLocationMinDistanceMult,

        fCombatTargetLocationPathingRadius,

        fCombatTargetLocationRadiusSizeMult,

        fCombatTargetLocationRepositionAngleMult,

        fCombatTargetLocationSwimmingOffset,

        fCombatTargetLocationWidthMax,

        fCombatTargetLocationWidthMin,

        fCombatTargetLocationWidthSizeMult,

        fCombatTeammateFollowRadiusBase,

        fCombatTeammateFollowRadiusMin,

        fCombatTeammateFollowRadiusMult,

        fCombatThreatAnticipateTime,

        fCombatThreatAvoidCost,

        fCombatThreatBufferRadius,

        fCombatThreatCacheVelocityTime,

        fCombatThreatDangerousObjectHealth,

        fCombatThreatExplosiveObjectThreatTime,

        fCombatThreatExtrudeTime,

        fCombatThreatExtrudeVelocityThreshold,

        fCombatThreatNegativeExtrudeTime,

        fCombatThreatProximityExplosionAvoidTime,

        fCombatThreatRatioUpdateTime,

        fCombatThreatSignificantScore,

        fCombatThreatTimedExplosionLength,

        fCombatThreatUpdateTimeMax,

        fCombatThreatUpdateTimeMin,

        fCombatThreatViewCone,

        fCombatUnarmedCritDamageMult,

        fCombatUnreachableTargetCheckTime,

        fCombatVulnerabilityMod,

        fCombatYieldRetryTime,

        fCombatYieldTime,

        fCommentOnPlayerActionsTimer,

        fCommentOnPlayerKnockingThings,

        fConcussionTimer,

        fConeProjectileEnvironmentDistance,

        fConeProjectileEnvironmentTimer,

        fConeProjectileForceBase,

        fConeProjectileForceMult,

        fConeProjectileForceMultAngular,

        fConeProjectileForceMultLinear,

        fConeProjectileWaterScaleMult,

        fCoverEvaluationLastSeenExpireTime,

        fCoverFiredProjectileExpireTime,

        fCoverFiringReloadClipPercent,

        fCoverWaitReloadClipPercent,

        fCoveredAdvanceMinAdvanceDistanceMax,

        fCoveredAdvanceMinAdvanceDistanceMin,

        fCrafting,

        fCreatureDefaultTurningSpeed,

        fCreditsScrollSpeed,

        fCrimeAlarmRespMult,

        fCrimeDispAttack,

        fCrimeDispMurder,

        fCrimeDispPersonal,

        fCrimeDispPickpocket,

        fCrimeDispSteal,

        fCrimeDispTresspass,

        fCrimeFavorMult,

        fCrimeGoldSkillPenaltyMult,

        fCrimeGoldSteal,

        fCrimePersonalRegardMult,

        fCrimeRegardMult,

        fCrimeSoundBase,

        fCrimeSoundMult,

        fCrimeWitnessRegardMult,

        fDOFDistanceMult,

        fDamageArmConditionBase,

        fDamageArmConditionMult,

        fDamageGunWeapCondBase,

        fDamageGunWeapCondMult,

        fDamageMeleeWeapCondBase,

        fDamageMeleeWeapCondMult,

        fDamagePCSkillMax,

        fDamagePCSkillMin,

        fDamageSkillMax,

        fDamageSkillMin,

        fDamageSneakAttackMult,

        fDamageStrengthBase,

        fDamageStrengthMult,

        fDamageToArmorPercentage,

        fDamageToWeaponEnergyMult,

        fDamageToWeaponGunMult,

        fDamageToWeaponLauncherMult,

        fDamageToWeaponMeleeMult,

        fDamageUnarmedPenalty,

        fDamageWeaponMult,

        fDamagedAVRegenDelay,

        fDamagedHealthRegenDelay,

        fDamagedMagickaRegenDelay,

        fDamagedStaminaRegenDelay,

        fDangerousObjectExplosionDamage,

        fDangerousObjectExplosionRadius,

        fDangerousProjectileExplosionDamage,

        fDangerousProjectileExplosionRadius,

        fDaytimeColorExtension,

        fDeadReactionDistance,

        fDeathForceDamageMax,

        fDeathForceDamageMin,

        fDeathForceForceMax,

        fDeathForceForceMin,

        fDeathForceMassBase,

        fDeathForceMassMult,

        fDeathForceRangedDamageMax,

        fDeathForceRangedDamageMin,

        fDeathForceRangedForceMax,

        fDeathForceRangedForceMin,

        fDeathForceSpellImpactMult,

        fDeathSoundMaxDistance,

        fDebrisFadeTime,

        fDecalLOD,

        fDecapitateBloodTime,

        fDefault,

        fDefaultAngleTolerance,

        fDefaultBowSpeedBonus,

        fDemandBase,

        fDemandMult,

        fDetectEventDistanceNPC,

        fDetectEventDistancePlayer,

        fDetectEventDistanceVeryLoudMult,

        fDetectEventSneakDistanceVeryLoud,

        fDetectProjectileDistanceNPC,

        fDetectProjectileDistancePlayer,

        fDetectionActionTimer,

        fDetectionCombatNonTargetDistanceMult,

        fDetectionCommentTimer,

        fDetectionEventExpireTime,

        fDetectionLOSDistanceAngle,

        fDetectionLOSDistanceMultExterior,

        fDetectionLOSDistanceMultInterior,

        fDetectionLargeActorSizeMult,

        fDetectionNightEyeBonus,

        fDetectionSneakLightMod,

        fDetectionStateExpireTime,

        fDetectionUpdateTimeMax,

        fDetectionUpdateTimeMaxComplex,

        fDetectionUpdateTimeMin,

        fDetectionUpdateTimeMinComplex,

        fDetectionViewCone,

        fDialogFocalDepthRange,

        fDialogFocalDepthStrength,

        fDialogSpeechDelaySeconds,

        fDialogZoomInSeconds,

        fDialogZoomOutSeconds,

        fDialogueHardStopAngle,

        fDialogueSoftStopAngle,

        fDiffMultHPByPCE,

        fDiffMultHPByPCH,

        fDiffMultHPByPCN,

        fDiffMultHPByPCVE,

        fDiffMultHPByPCVH,

        fDiffMultHPToPCE,

        fDiffMultHPToPCH,

        fDiffMultHPToPCN,

        fDiffMultHPToPCVE,

        fDiffMultHPToPCVH,

        fDiffMultXPE,

        fDiffMultXPH,

        fDiffMultXPN,

        fDiffMultXPVE,

        fDiffMultXPVH,

        fDifficultyDamageMultiplier,

        fDifficultyDefaultValue,

        fDifficultyMaxValue,

        fDifficultyMinValue,

        fDisarmedPickupWeaponDistanceMult,

        fDisenchantSkillUse,

        fDistanceAutomaticallyActivateDoor,

        fDistanceExteriorReactCombat,

        fDistanceFadeActorAutoLoadDoor,

        fDistanceInteriorReactCombat,

        fDistanceProjectileExplosionDetection,

        fDistancetoPlayerforConversations,

        fDrinkRepeatRate,

        fDyingTimer,

        fEmbeddedWeaponSwitchChance,

        fEmbeddedWeaponSwitchTime,

        fEnchantingCostExponent,

        fEnchantingRuleOfFive,

        fEnchantingRuleOfTen,

        fEnchantingRuleOfTwo,

        fEnchantingSkillCostBase,

        fEnchantingSkillCostMult,

        fEnchantingSkillCostScale,

        fEnchantingSkillFactor,

        fEnchantmentEffectPointsMult,

        fEnchantmentGoldMult,

        fEnchantmentPointsMult,

        fEnemyHealthBarTimer,

        fEnvMapLOD,

        fEssentialDeathTime,

        fEssentialDownCombatHealthRegenMult,

        fEssentialHealthPercentReGain,

        fEssentialNPCMinimumHealth,

        fEssentialNonCombatHealRateBonus,

        fEvaluatePackageTimer,

        fEvaluateProcedureTimer,

        fExplodeLimbRemovalDelay,

        fExplodeLimbRemovalDelayVATS,

        fExplosionForceClutterUpBias,

        fExplosionForceKnockdownMinimum,

        fExplosionForceMultAngular,

        fExplosionForceMultLinear,

        fExplosionImageSpaceSwapPower,

        fExplosionKnockStateExplodeDownTime,

        fExplosionLOSBuffer,

        fExplosionLOSBufferDistance,

        fExplosionMaxImpulse,

        fExplosionSourceRefMult,

        fExplosionSplashRadius,

        fExplosionWaterRadiusRatio,

        fExplosiveProjectileBlockedResetTime,

        fExplosiveProjectileBlockedWaitTime,

        fExpressionChangePerSec,

        fExpressionStrengthAdd,

        fEyeEnvMapLOD,

        fEyeHeadingMaxOffsetEmotionAngry,

        fEyeHeadingMaxOffsetEmotionFear,

        fEyeHeadingMaxOffsetEmotionHappy,

        fEyeHeadingMaxOffsetEmotionNeutral,

        fEyeHeadingMaxOffsetEmotionSad,

        fEyeHeadingMinOffsetEmotionAngry,

        fEyeHeadingMinOffsetEmotionFear,

        fEyeHeadingMinOffsetEmotionHappy,

        fEyeHeadingMinOffsetEmotionNeutral,

        fEyeHeadingMinOffsetEmotionSad,

        fEyePitchMaxOffsetEmotionAngry,

        fEyePitchMaxOffsetEmotionFear,

        fEyePitchMaxOffsetEmotionHappy,

        fEyePitchMaxOffsetEmotionNeutral,

        fEyePitchMaxOffsetEmotionSad,

        fEyePitchMinOffsetEmotionAngry,

        fEyePitchMinOffsetEmotionFear,

        fEyePitchMinOffsetEmotionHappy,

        fEyePitchMinOffsetEmotionNeutral,

        fEyePitchMinOffsetEmotionSad,

        fFallLegDamageMult,

        fFastTravelSpeedMult,

        fFastWalkInterpolationBetweenWalkAndRun,

        fFavorCostActivator,

        fFavorCostAttack,

        fFavorCostAttackCrimeMult,

        fFavorCostLoadDoor,

        fFavorCostNonLoadDoor,

        fFavorCostOwnedDoorMult,

        fFavorCostStealContainerCrime,

        fFavorCostStealContainerMult,

        fFavorCostStealObjectMult,

        fFavorCostTakeObject,

        fFavorCostUnlockContainer,

        fFavorCostUnlockDoor,

        fFavorEventStopDistance,

        fFavorEventTriggerDistance,

        fFavorRequestPickDistance,

        fFavorRequestRadius,

        fFavorRequestWaitTimer,

        fFirstPersonLookAtVertOffset,

        fFleeDistanceExterior,

        fFleeDistanceInterior,

        fFleeDoneDistanceExterior,

        fFleeDoneDistanceInterior,

        fFleeIsSafeTimer,

        fFloatQuestMarkerFloatHeight,

        fFloatQuestMarkerMaxDistance,

        fFloatQuestMarkerMinDistance,

        fFlyingActorDefaultTurningSpeed,

        fFollowExtraCatchUpSpeedMult,

        fFollowMatchSpeedZoneWidth,

        fFollowRunMaxSpeedupMultiplier,

        fFollowRunMinSlowdownMultiplier,

        fFollowSlowdownZoneWidth,

        fFollowSpaceBetweenFollowers,

        fFollowStartSprintDistance,

        fFollowStopZoneMinMult,

        fFollowWalkMaxSpeedupMultiplier,

        fFollowWalkMinSlowdownMultiplier,

        fFollowWalkZoneMult,

        fFollowerSpacingAtDoors,

        fFriendHitTimer,

        fFriendMinimumLastHitTime,

        fFurnitureMarkerAngleTolerance,

        fFurnitureScaleAnimDurationNPC,

        fFurnitureScaleAnimDurationPlayer,

        fGameplayImpulseMinMass,

        fGameplayImpulseMultBiped,

        fGameplayImpulseMultClutter,

        fGameplayImpulseMultDebrisLarge,

        fGameplayImpulseMultProp,

        fGameplayImpulseMultTrap,

        fGameplayImpulseScale,

        fGameplaySpeakingEmotionMaxChangeValue,

        fGameplaySpeakingEmotionMinChangeValue,

        fGameplayVoiceFilePadding,

        fGameplayiSpeakingEmotionMaxDeltaChange,

        fGameplayiSpeakingEmotionMinDeltaChange,

        fGetHitPainMult,

        fGrabMaxWeightRunning,

        fGrabMaxWeightWalking,

        fGrenadeAgeMax,

        fGrenadeFriction,

        fGrenadeHighArcSpeedPercentage,

        fGrenadeRestitution,

        fGrenadeThrowHitFractionThreshold,

        fGuardPackageAttackRadiusMult,

        fGunDecalCameraDistance,

        fGunParticleCameraDistance,

        fGunReferenceSkill,

        fGunShellCameraDistance,

        fGunShellDirectionRandomize,

        fGunShellEjectSpeed,

        fGunShellLifetime,

        fGunShellRotateRandomize,

        fGunShellRotateSpeed,

        fGunSpreadArmBase,

        fGunSpreadArmMult,

        fGunSpreadCondBase,

        fGunSpreadCondMult,

        fGunSpreadCrouchBase,

        fGunSpreadCrouchMult,

        fGunSpreadDriftBase,

        fGunSpreadDriftMult,

        fGunSpreadHeadBase,

        fGunSpreadHeadMult,

        fGunSpreadIronSightsBase,

        fGunSpreadIronSightsMult,

        fGunSpreadNPCArmBase,

        fGunSpreadNPCArmMult,

        fGunSpreadRunBase,

        fGunSpreadRunMult,

        fGunSpreadSkillBase,

        fGunSpreadSkillMult,

        fGunSpreadWalkBase,

        fGunSpreadWalkMult,

        fHUDCompassLocationMaxDist,

        fHUDOpacity,

        fHandDamageSkillBase,

        fHandDamageSkillMult,

        fHandDamageStrengthBase,

        fHandDamageStrengthMult,

        fHandHealthMax,

        fHandHealthMin,

        fHandReachDefault,

        fHavokTauRatio,

        fHazardDropMaxDistance,

        fHazardMaxWaitTime,

        fHazardSpacingMult,

        fHeadTrackSpeedMax,

        fHeadTrackSpeedMaxAngle,

        fHeadTrackSpeedMin,

        fHeadTrackSpeedMinAngle,

        fHeadingMarkerAngleTolerance,

        fHealthRegenDelayMax,

        fHorseMountOffsetX,

        fHorseMountOffsetY,

        fHostileActorExteriorDistance,

        fHostileActorInteriorDistance,

        fHostileFlyingActorExteriorDistance,

        fIdleChatterCommentTimer,

        fIdleChatterCommentTimerMax,

        fIdleMarkerAngleTolerance,

        fImpactShaderMaxDistance,

        fImpactShaderMaxMagnitude,

        fImpactShaderMinMagnitude,

        fIntimidateConfidenceMultAverage,

        fIntimidateConfidenceMultBrave,

        fIntimidateConfidenceMultCautious,

        fIntimidateConfidenceMultCowardly,

        fIntimidateConfidenceMultFoolhardy,

        fIntimidateSpeechcraftCurve,

        fInventory,

        fInventoryLight,

        fInventoryMenuLight,

        fIronSightsDOFDistance,

        fIronSightsDOFRange,

        fIronSightsDOFStrengthCap,

        fIronSightsDOFSwitchSeconds,

        fIronSightsFOVTimeChange,

        fIronSightsGunMotionBlur,

        fIronSightsMotionBlur,

        fItemPointsMult,

        fItemRepairCostMult,

        fJogInterpolationBetweenWalkAndRun,

        fJumpDoubleMult,

        fJumpFallHeightExponent,

        fJumpFallHeightExponentNPC,

        fJumpFallHeightMin,

        fJumpFallHeightMinNPC,

        fJumpFallHeightMult,

        fJumpFallHeightMultNPC,

        fJumpFallRiderMult,

        fJumpFallSkillBase,

        fJumpFallSkillMult,

        fJumpFallVelocityMin,

        fJumpHeightMin,

        fJumpMoveBase,

        fJumpMoveMult,

        fJumpSwimmingMult,

        fKarmaModKillingEvilActor,

        fKarmaModMurderingNonEvilCreature,

        fKarmaModMurderingNonEvilNPC,

        fKarmaModStealing,

        fKillCamBaseOdds,

        fKillCamLevelBias,

        fKillCamLevelFactor,

        fKillCamLevelMaxBias,

        fKillMoveMaxDuration,

        fKillWitnessesTimerSetting,

        fKnockbackAgilBase,

        fKnockbackAgilMult,

        fKnockbackDamageBase,

        fKnockbackDamageMult,

        fKnockbackForceMax,

        fKnockbackTime,

        fKnockdownAgilBase,

        fKnockdownAgilMult,

        fKnockdownBaseHealthThreshold,

        fKnockdownChance,

        fKnockdownCurrentHealthThreshold,

        fKnockdownDamageBase,

        fKnockdownDamageMult,

        fLargeProjectilePickBufferSize,

        fLargeProjectileSize,

        fLevelUpCarryWeightMod,

        fLightRecalcTimer,

        fLightRecalcTimerPlayer,

        fLoadingWheelScale,

        fLockLevelBase,

        fLockLevelMult,

        fLockPickBreakBase,

        fLockPickBreakMult,

        fLockPickQualityBase,

        fLockPickQualityMult,

        fLockSkillBase,

        fLockSkillMult,

        fLockTrapGoOffBase,

        fLockTrapGoOffMult,

        fLockpickBreakAdept,

        fLockpickBreakApprentice,

        fLockpickBreakExpert,

        fLockpickBreakMaster,

        fLockpickBreakNovice,

        fLockpickBreakSkillBase,

        fLockpickBreakSkillMult,

        fLockpickBrokenPicksMult,

        fLockpickSkillPartialPickBase,

        fLockpickSkillPartialPickMult,

        fLockpickSkillSweetSpotBase,

        fLockpickSkillSweetSpotMult,

        fLookDownDisableBlinkingAmt,

        fLookGraphX,

        fLookGraphY,

        fLowHealthTutorialPercentage,

        fLowLevelNPCBaseHealthMult,

        fLowMagickaTutorialPercentage,

        fLowStaminaTutorialPercentage,

        fMagic,

        fMagicAbsorbDistanceReachMult,

        fMagicAbsorbVisualTimer,

        fMagicAccumulatingModifierEffectHoldDuration,

        fMagicAreaBaseCostMult,

        fMagicAreaScale,

        fMagicAreaScaleMax,

        fMagicAreaScaleMin,

        fMagicBarrierDepth,

        fMagicBarrierHeight,

        fMagicBarrierSpacing,

        fMagicBoltDuration,

        fMagicBoltSegmentLength,

        fMagicCEEnchantMagOffset,

        fMagicCasterPCSkillCostBase,

        fMagicCasterPCSkillCostMult,

        fMagicCasterSkillCostBase,

        fMagicCasterSkillCostMult,

        fMagicChainExplosionEffectivenessDelta,

        fMagicCloudAreaMin,

        fMagicCloudDurationMin,

        fMagicCloudFindTargetTime,

        fMagicCloudLifeScale,

        fMagicCloudSizeScale,

        fMagicCloudSlowdownRate,

        fMagicCloudSpeedBase,

        fMagicCloudSpeedScale,

        fMagicCostScale,

        fMagicDefaultAccumulatingModifierEffectRate,

        fMagicDefaultTouchDistance,

        fMagicDiseaseTransferBase,

        fMagicDiseaseTransferMult,

        fMagicDispelMagnitudeMult,

        fMagicDualCastingCostBase,

        fMagicDualCastingCostMult,

        fMagicDualCastingEffectivenessBase,

        fMagicDualCastingEffectivenessMult,

        fMagicDualCastingTimeBase,

        fMagicDualCastingTimeMult,

        fMagicDurMagBaseCostMult,

        fMagicEnchantmentChargeBase,

        fMagicEnchantmentChargeMult,

        fMagicEnchantmentDrainBase,

        fMagicEnchantmentDrainMult,

        fMagicExplosionAgilityMult,

        fMagicExplosionClutterMult,

        fMagicExplosionIncorporealMult,

        fMagicExplosionIncorporealTime,

        fMagicExplosionPowerBase,

        fMagicExplosionPowerMax,

        fMagicExplosionPowerMin,

        fMagicExplosionPowerMult,

        fMagicGuideSpacing,

        fMagicLightForwardOffset,

        fMagicLightHeightOffset,

        fMagicLightRadiusBase,

        fMagicLightSideOffset,

        fMagicNightEyeAmbient,

        fMagicPCSkillCostScale,

        fMagicPlayerMinimumInvisibility,

        fMagicPostDrawCastDelay,

        fMagicProjectileMaxDistance,

        fMagicRangeTargetCostMult,

        fMagicResistActorSkillBase,

        fMagicResistActorSkillMult,

        fMagicResistTargetWillpowerBase,

        fMagicResistTargetWillpowerMult,

        fMagicSkillCostScale,

        fMagicSummonMaxAppearTime,

        fMagicTelekinesiDistanceMult,

        fMagicTelekinesisBaseDistance,

        fMagicTelekinesisComplexMaxForce,

        fMagicTelekinesisComplexObjectDamping,

        fMagicTelekinesisComplexSpringDamping,

        fMagicTelekinesisComplexSpringElasticity,

        fMagicTelekinesisDamageBase,

        fMagicTelekinesisDamageMult,

        fMagicTelekinesisDistanceMin,

        fMagicTelekinesisDualCastDamageMult,

        fMagicTelekinesisDualCastThrowMult,

        fMagicTelekinesisLiftPowerMult,

        fMagicTelekinesisMaxForce,

        fMagicTelekinesisMoveAccelerate,

        fMagicTelekinesisMoveBase,

        fMagicTelekinesisMoveMax,

        fMagicTelekinesisObjectDamping,

        fMagicTelekinesisSpringDamping,

        fMagicTelekinesisSpringElasticity,

        fMagicTelekinesisThrow,

        fMagicTelekinesisThrowAccelerate,

        fMagicTelekinesisThrowMax,

        fMagicTrackingLimit,

        fMagicTrackingLimitComplex,

        fMagicTrackingMultBall,

        fMagicTrackingMultBolt,

        fMagicTrackingMultFog,

        fMagicUnitsPerFoot,

        fMagicVACNoPartTargetedMult,

        fMagicVACPartTargetedMult,

        fMagicWardPowerMaxBase,

        fMagicWardPowerMaxMult,

        fMagickaRegenDelayMax,

        fMagickaReturnBase,

        fMagickaReturnMult,

        fMapMarkerMaxPercentSize,

        fMapMarkerMinFadeAlpha,

        fMapMarkerMinPercentSize,

        fMapQuestMarkerMaxPercentSize,

        fMapQuestMarkerMinFadeAlpha,

        fMapQuestMarkerMinPercentSize,

        fMasserAngleFadeEnd,

        fMasserAngleFadeStart,

        fMasserAngleShadowEarlyFade,

        fMasserSpeed,

        fMasserZOffset,

        fMaxArmorRating,

        fMaxSandboxRescanSeconds,

        fMaxSellMult,

        fMaximumWind,

        fMeleeMovementRestrictionsUpdateTime,

        fMeleeSweepViewAngleMult,

        fMeshLODLevel,

        fMinBuyMult,

        fMinDistanceUseHorse,

        fMinSandboxRescanSeconds,

        fMineAgeMax,

        fMineExteriorRadiusMult,

        fMinesBlinkFast,

        fMinesBlinkMax,

        fMinesBlinkSlow,

        fMinesDelayMin,

        fModelReferenceEffectMaxWaitTime,

        fMostCommonProjectileCollisionRadius,

        fMotionBlur,

        fMountedMaxLookingDown,

        fMoveCharRunBase,

        fMoveCharWalkBase,

        fMoveEncumEffect,

        fMoveEncumEffectNoWeapon,

        fMoveFlyRunMult,

        fMoveFlyWalkMult,

        fMoveGraphX,

        fMoveGraphY,

        fMoveSprintMult,

        fMoveSwimMult,

        fMoveWeightMax,

        fMoveWeightMin,

        fMovementNearTargetAvoidCost,

        fMovementNearTargetAvoidRadius,

        fMovementTargetAvoidCost,

        fMovementTargetAvoidRadius,

        fMovementTargetAvoidRadiusMult,

        fNPCAttributeHealthMult,

        fNPCBaseMagickaMult,

        fNPCHealthLevelBonus,

        fNear,

        fObjectHitH,

        fObjectHitTwoHandReach,

        fObjectHitWeaponReach,

        fObjectMotionBlur,

        fObjectWeightPickupDetectionMult,

        fOutOfBreathStaminaRegenDelay,

        fPCBaseHealthMult,

        fPCBaseMagickaMult,

        fPCHealthLevelBonus,

        fPainDelay,

        fPartialPickAverage,

        fPartialPickEasy,

        fPartialPickHard,

        fPartialPickVeryEasy,

        fPartialPickVeryHard,

        fPerceptionMult,

        fPerkHeavyArmorExpertSpeedMult,

        fPerkHeavyArmorJourneymanDamageMult,

        fPerkHeavyArmorMasterSpeedMult,

        fPerkHeavyArmorNoviceDamageMult,

        fPerkHeavyArmorSinkGravityMult,

        fPerkLightArmorExpertSpeedMult,

        fPerkLightArmorJourneymanDamageMult,

        fPerkLightArmorMasterRatingMult,

        fPerkLightArmorNoviceDamageMult,

        fPersAdmireAggr,

        fPersAdmireConf,

        fPersAdmireEner,

        fPersAdmireIntel,

        fPersAdmirePers,

        fPersAdmireResp,

        fPersAdmireStre,

        fPersAdmireWillp,

        fPersBoastAggr,

        fPersBoastConf,

        fPersBoastEner,

        fPersBoastIntel,

        fPersBoastPers,

        fPersBoastResp,

        fPersBoastStre,

        fPersBoastWillp,

        fPersBullyAggr,

        fPersBullyConf,

        fPersBullyEner,

        fPersBullyIntel,

        fPersBullyPers,

        fPersBullyResp,

        fPersBullyStre,

        fPersBullyWillp,

        fPersJokeAggr,

        fPersJokeConf,

        fPersJokeEner,

        fPersJokeIntel,

        fPersJokePers,

        fPersJokeResp,

        fPersJokeStre,

        fPersJokeWillp,

        fPersuasionAccuracyMaxDisposition,

        fPersuasionAccuracyMaxSelect,

        fPersuasionAccuracyMinDispostion,

        fPersuasionAccuracyMinSelect,

        fPersuasionBaseValueMaxDisposition,

        fPersuasionBaseValueMaxSelect,

        fPersuasionBaseValueMinDispostion,

        fPersuasionBaseValueMinSelect,

        fPersuasionBaseValueShape,

        fPersuasionMaxDisposition,

        fPersuasionMaxInput,

        fPersuasionMaxSelect,

        fPersuasionMinDispostion,

        fPersuasionMinInput,

        fPersuasionMinPercentCircle,

        fPersuasionMinSelect,

        fPersuasionShape,

        fPhysicsDamage,

        fPhysicsDamageSpeedBase,

        fPhysicsDamageSpeedMin,

        fPhysicsDamageSpeedMult,

        fPickLevelBase,

        fPickLevelMult,

        fPickMaxAngle,

        fPickNumBase,

        fPickNumMult,

        fPickPocketActorSkillBase,

        fPickPocketActorSkillMult,

        fPickPocketAmountBase,

        fPickPocketAmountMult,

        fPickPocketDetected,

        fPickPocketMaxChance,

        fPickPocketMinChance,

        fPickPocketTargetSkillBase,

        fPickPocketTargetSkillMult,

        fPickPocketWeightBase,

        fPickPocketWeightMult,

        fPickSpring,

        fPickUpWeaponDelay,

        fPickpocketSkillUsesCurve,

        fPickupItemDistanceFudge,

        fPickupWeaponDistanceMinMaxDPSMult,

        fPickupWeaponMeleeDistanceMax,

        fPickupWeaponMeleeDistanceMin,

        fPickupWeaponMeleeWeaponDPSMult,

        fPickupWeaponMinDPSImprovementPercent,

        fPickupWeaponRangedDistanceMax,

        fPickupWeaponRangedDistanceMin,

        fPickupWeaponRangedMeleeDPSRatioThreshold,

        fPickupWeaponTargetUnreachableDistanceMult,

        fPickupWeaponUnarmedDistanceMax,

        fPickupWeaponUnarmedDistanceMin,

        fPlayerDeathReloadTime,

        fPlayerDetectActorValue,

        fPlayerDetectionSneakBase,

        fPlayerDetectionSneakMult,

        fPlayerDropDistance,

        fPlayerHealthHeartbeatFast,

        fPlayerHealthHeartbeatSlow,

        fPlayerMaxResistance,

        fPlayerTargetCombatDistance,

        fPlayerTeleportFadeSeconds,

        fPlayerVelocityCacheTime,

        fPlayerVelocitySampleInterval,

        fPlayerVelocitySampleTime,

        fPotionGoldValueMult,

        fPotionMortPestleMult,

        fPotionT,

        fPowerAttackCoolDownTime,

        fPowerAttackDefaultBonus,

        fPowerAttackStaminaPenalty,

        fProjectileCollisionImpulseScale,

        fProjectileDefaultTracerRange,

        fProjectileDeflectionTime,

        fProjectileInventoryGrenadeFreakoutTime,

        fProjectileInventoryGrenadeTimer,

        fProjectileKnockMinMass,

        fProjectileKnockMultBiped,

        fProjectileKnockMultClutter,

        fProjectileKnockMultProp,

        fProjectileKnockMultTrap,

        fProjectileMaxDistance,

        fProjectileReorientTracerMin,

        fQuestCinematicCharacterFadeIn,

        fQuestCinematicCharacterFadeInDelay,

        fQuestCinematicCharacterFadeOut,

        fQuestCinematicCharacterRemain,

        fQuestCinematicObjectiveFadeIn,

        fQuestCinematicObjectiveFadeInDelay,

        fQuestCinematicObjectiveFadeOut,

        fQuestCinematicObjectivePauseTime,

        fQuestCinematicObjectiveScrollTime,

        fRandomSceneAgainMaxTime,

        fRandomSceneAgainMinTime,

        fReEquipArmorTime,

        fRechargeGoldMult,

        fReflectedAbsorbChanceReduction,

        fRelationshipBase,

        fRelationshipMult,

        fRemoteCombatMissedAttack,

        fRemoveExcessComplexDeadTime,

        fRemoveExcessDeadTime,

        fRepairMax,

        fRepairMin,

        fRepairScavengeMult,

        fRepairSkillBase,

        fRepairSkillMax,

        fReservationExpirationSeconds,

        fResistArrestTimer,

        fRockitDamageBonusWeightMin,

        fRockitDamageBonusWeightMult,

        fRoomLightingTransitionDuration,

        fRumbleBlockStrength,

        fRumbleBlockTime,

        fRumbleHitBlockedStrength,

        fRumbleHitBlockedTime,

        fRumbleHitStrength,

        fRumbleHitTime,

        fRumblePainStrength,

        fRumblePainTime,

        fRumbleShakeRadiusMult,

        fRumbleShakeTimeMult,

        fRumbleStruckStrength,

        fRumbleStruckTime,

        fSandBoxDelayEvalSeconds,

        fSandBoxExtraDialogueRange,

        fSandBoxInterMarkerMinDist,

        fSandBoxRadiusHysteresis,

        fSandBoxSearchRadius,

        fSandboxBreakfastMax,

        fSandboxBreakfastMin,

        fSandboxCylinderBottom,

        fSandboxCylinderTop,

        fSandboxDinnerMax,

        fSandboxDinnerMin,

        fSandboxDurationBase,

        fSandboxDurationMultEatSitting,

        fSandboxDurationMultEatStanding,

        fSandboxDurationMultFurniture,

        fSandboxDurationMultIdleMarker,

        fSandboxDurationMultSitting,

        fSandboxDurationMultSleeping,

        fSandboxDurationMultWandering,

        fSandboxDurationRangeMult,

        fSandboxEnergyMult,

        fSandboxEnergyMultEatSitting,

        fSandboxEnergyMultEatStanding,

        fSandboxEnergyMultFurniture,

        fSandboxEnergyMultIdleMarker,

        fSandboxEnergyMultSitting,

        fSandboxEnergyMultSleeping,

        fSandboxEnergyMultWandering,

        fSandboxLunchMax,

        fSandboxLunchMin,

        fSandboxMealDurationMax,

        fSandboxMealDurationMin,

        fSandboxSleepDurationMax,

        fSandboxSleepDurationMin,

        fSandboxSleepStartMax,

        fSandboxSleepStartMin,

        fSayOncePerDayInfoTimer,

        fScrollCostMult,

        fSecondsBetweenWindowUpdate,

        fSecundaAngleFadeEnd,

        fSecundaAngleFadeStart,

        fSecundaAngleShadowEarlyFade,

        fSecundaSpeed,

        fSecundaZOffset,

        fShieldBaseFactor,

        fShieldBashMax,

        fShieldBashMin,

        fShieldBashPCMax,

        fShieldBashPCMin,

        fShieldBashSkillUseBase,

        fShieldBashSkillUseMult,

        fShieldScalingFactor,

        fShockBoltGrowWidth,

        fShockBoltSmallWidth,

        fShockBoltsLength,

        fShockBoltsRadius,

        fShockBoltsRadiusStrength,

        fShockBranchBoltsRadius,

        fShockBranchBoltsRadiusStrength,

        fShockBranchLifetime,

        fShockBranchSegmentLength,

        fShockBranchSegmentVariance,

        fShockCastVOffset,

        fShockCoreColorB,

        fShockCoreColorG,

        fShockCoreColorR,

        fShockGlowColorB,

        fShockGlowColorG,

        fShockGlowColorR,

        fShockSegmentLength,

        fShockSegmentVariance,

        fShockSubSegmentVariance,

        fShoutTime,

        fShoutTimeout,

        fSittingMaxLookingDown,

        fSkillUsageLockPickAverage,

        fSkillUsageLockPickBroken,

        fSkillUsageLockPickEasy,

        fSkillUsageLockPickHard,

        fSkillUsageLockPickVeryEasy,

        fSkillUsageLockPickVeryHard,

        fSkillUsageRechargeMult,

        fSkillUsageSneakHidden,

        fSkillUsageSneakPerSecond,

        fSkillUseCurve,

        fSkinnedDecalLOD,

        fSkyCellRefFadeDistance,

        fSmallBumpSpeed,

        fSmithingArmorMax,

        fSmithingConditionFactor,

        fSmithingWeaponMax,

        fSneakActionMult,

        fSneakAlertMod,

        fSneakAmbushNonTargetMod,

        fSneakAmbushTargetMod,

        fSneakAttackSkillUsageMelee,

        fSneakAttackSkillUsageRanged,

        fSneakBaseValue,

        fSneakCombatMod,

        fSneakDetectionSizeLarge,

        fSneakDetectionSizeNormal,

        fSneakDetectionSizeSmall,

        fSneakDetectionSizeVeryLarge,

        fSneakDistanceAttenuationExponent,

        fSneakEquippedWeightBase,

        fSneakEquippedWeightMult,

        fSneakExteriorDistanceMult,

        fSneakFlyingDistanceMult,

        fSneakLightExteriorMult,

        fSneakLightMoveMult,

        fSneakLightMult,

        fSneakLightRunMult,

        fSneakMaxDistance,

        fSneakNoticeMin,

        fSneakPerceptionSkillMax,

        fSneakPerceptionSkillMin,

        fSneakRunningMult,

        fSneakSizeBase,

        fSneakSkillMult,

        fSneakSleepBonus,

        fSneakSleepMod,

        fSneakSoundLosMult,

        fSneakSoundsMult,

        fSneakStealthBoyMult,

        fSortActorDistanceListTimer,

        fSpeechCraftBase,

        fSpeechCraftMult,

        fSpeechDelay,

        fSpeechcraftFavorMax,

        fSpeechcraftFavorMin,

        fSpellCastingDetectionHitActorMod,

        fSpellCastingDetectionMod,

        fSpellmakingGoldMult,

        fSplashScale,

        fSplashSoundHeavy,

        fSplashSoundLight,

        fSplashSoundMedium,

        fSplashSoundOutMult,

        fSplashSoundTimer,

        fSplashSoundVelocityMult,

        fSprintEncumbranceMult,

        fSprintStaminaDrainMult,

        fSprintStaminaWeightBase,

        fSprintStaminaWeightMult,

        fStagger,

        fStaggerAttackBase,

        fStaggerAttackMult,

        fStaggerBlockAttackBase,

        fStaggerBlockAttackMult,

        fStaggerBlockAttackShieldBase,

        fStaggerBlockAttackShieldMult,

        fStaggerBlockBase,

        fStaggerBlockMult,

        fStaggerBlockingMult,

        fStaggerMassBase,

        fStaggerMassMult,

        fStaggerMassOffsetBase,

        fStaggerMassOffsetMult,

        fStaggerMaxDuration,

        fStaggerMin,

        fStaggerPlayerMassMult,

        fStaggerRecoilingMult,

        fStaggerRunningMult,

        fStaggerShieldMult,

        fStaminaAttackWeaponBase,

        fStaminaAttackWeaponMult,

        fStaminaBashBase,

        fStaminaBlockBase,

        fStaminaBlockDmgMult,

        fStaminaBlockStaggerMult,

        fStaminaPowerBashBase,

        fStaminaRegenDelayMax,

        fStarsRotateDays,

        fStarsRotateXAxis,

        fStarsRotateYAxis,

        fStatsCameraFOV,

        fStatsCameraNearDistance,

        fStatsHealthLevelMult,

        fStatsHealthStartMult,

        fStatsLineScale,

        fStatsRotationRampTime,

        fStatsRotationSpeedMax,

        fStatsSkillsLookAtX,

        fStatsSkillsLookAtY,

        fStatsSkillsLookAtZ,

        fStatsStarCameraOffsetX,

        fStatsStarCameraOffsetY,

        fStatsStarCameraOffsetZ,

        fStatsStarLookAtX,

        fStatsStarLookAtY,

        fStatsStarLookAtZ,

        fStatsStarScale,

        fStatsStarXIncrement,

        fStatsStarYIncrement,

        fStatsStarZIncrement,

        fStatsStarZInitialOffset,

        fSubSegmentVariance,

        fSubmergedAngularDamping,

        fSubmergedLODDistance,

        fSubmergedLinearDampingH,

        fSubmergedLinearDampingV,

        fSubmergedMaxSpeed,

        fSubmergedMaxWaterDistance,

        fSubtitleSpeechDelay,

        fSummonDistanceCheckThreshold,

        fSummonedCreatureFadeOutSeconds,

        fSummonedCreatureMaxFollowDist,

        fSummonedCreatureMinFollowDist,

        fSummonedCreatureSearchRadius,

        fSunAlphaTransTime,

        fSunDirXExtreme,

        fSunMinimumGlareScale,

        fSunReduceGlareSpeed,

        fSunXExtreme,

        fSunYExtreme,

        fSunZExtreme,

        fSweetSpotAverage,

        fSweetSpotEasy,

        fSweetSpotHard,

        fSweetSpotVeryEasy,

        fSweetSpotVeryHard,

        fTakeBackTimerSetting,

        fTargetMovedCoveredMoveRepathLength,

        fTargetMovedRepathLength,

        fTargetMovedRepathLengthLow,

        fTeammateAggroOnDistancefromPlayer,

        fTeleportDoorActivateDelayTimer,

        fTemperingSkillUseMult,

        fTimerForPlayerFurnitureEnter,

        fTorchEvaluationTimer,

        fTorchLightLevelInterior,

        fTorchLightLevelMorning,

        fTorchLightLevelNight,

        fTrackDeadZoneXY,

        fTrackDeadZoneZ,

        fTrackEyeXY,

        fTrackEyeZ,

        fTrackFudgeXY,

        fTrackFudgeZ,

        fTrackJustAcquiredDuration,

        fTrackMaxZ,

        fTrackMinZ,

        fTrackSpeed,

        fTrackXY,

        fTrainingBaseCost,

        fTrainingMultCost,

        fTriggerAvoidPlayerDistance,

        fUIAltLogoModel,

        fUILogoModel,

        fUIMistMenu,

        fUIMistModel,

        fUIPlayerSceneLight,

        fUIRaceSexLight,

        fUnarmedCreatureDPSMult,

        fUnarmedDamageMult,

        fUnarmedNPCDPSMult,

        fUnderwaterFullDepth,

        fVATSAutomaticMeleeDamageMult,

        fVATSCamTransRBDownStart,

        fVATSCamTransRBRampDown,

        fVATSCamTransRBRampup,

        fVATSCamTransRBStart,

        fVATSCamTransRBStrengthCap,

        fVATSCamZoomInTime,

        fVATSCameraMaxTime,

        fVATSCameraMinTime,

        fVATSCriticalChanceBonus,

        fVATSDOFRange,

        fVATSDOFStrengthCap,

        fVATSDOFSwitchSeconds,

        fVATSDamageToWeaponMult,

        fVATSDestructibleMult,

        fVATSDistanceFactor,

        fVATSGrenadeChanceMult,

        fVATSGrenadeDistAimZMult,

        fVATSGrenadeRangeMin,

        fVATSGrenadeRangeMult,

        fVATSGrenadeSkillFactor,

        fVATSGrenadeSuccessExplodeTimer,

        fVATSGrenadeSuccessMaxDistance,

        fVATSGrenadeTargetArea,

        fVATSGrenadeTargetMelee,

        fVATSH,

        fVATSHitChanceMult,

        fVATSImageSpaceTransitionTime,

        fVATSLimbSelectCamPanTime,

        fVATSMaxChance,

        fVATSMaxEngageDistance,

        fVATSMeleeArmConditionBase,

        fVATSMeleeArmConditionMult,

        fVATSMeleeChanceMult,

        fVATSMeleeMaxDistance,

        fVATSMeleeReachMult,

        fVATSMeleeWarpDistanceMult,

        fVATSMoveCameraLimbMult,

        fVATSMoveCameraLimbPercent,

        fVATSMoveCameraMaxSpeed,

        fVATSMoveCameraXPercent,

        fVATSMoveCameraYPercent,

        fVATSParalyzePalmChance,

        fVATSPlaybackDelay,

        fVATSPlayerDamageMult,

        fVATSPlayerMagicTimeSlowdownMult,

        fVATSPlayerTimeUpdateMult,

        fVATSRadialRampup,

        fVATSRadialStart,

        fVATSRadialStrength,

        fVATSRangeSpreadMax,

        fVATSRangeSpreadUncertainty,

        fVATSScreenPercentFactor,

        fVATSShotBurstTime,

        fVATSShotLongBurstTime,

        fVATSSkillFactor,

        fVATSSmartCameraCheckHeight,

        fVATSSmartCameraCheckStepCount,

        fVATSSmartCameraCheckStepDistance,

        fVATSSpreadMult,

        fVATSStealthMult,

        fVATSStrangerDistance,

        fVATSStrangerOdds,

        fVATSTargetActorHeightPanMult,

        fVATSTargetActorZMultFar,

        fVATSTargetActorZMultFarDist,

        fVATSTargetActorZMultNear,

        fVATSTargetFOVMinDist,

        fVATSTargetFOVMinFOV,

        fVATSTargetFOVMultFar,

        fVATSTargetFOVMultFarDist,

        fVATSTargetFOVMultNear,

        fVATSTargetRotateMult,

        fVATSTargetScanRotateMult,

        fVATSTargetSelectCamPanTime,

        fVATSTargetTimeUpdateMult,

        fVATSThrownWeaponRangeMult,

        fValueofItemForNoOwnership,

        fVatsShotgunSpreadRatio,

        fVoiceRateBase,

        fWardAngleForExplosions,

        fWarningTimer,

        fWaterKnockdownSizeLarge,

        fWaterKnockdownSizeNormal,

        fWaterKnockdownSizeSmall,

        fWaterKnockdownSizeVeryLarge,

        fWaterKnockdownVelocity,

        fWeaponBashMax,

        fWeaponBashMin,

        fWeaponBashPCMax,

        fWeaponBashPCMin,

        fWeaponBashSkillUseBase,

        fWeaponBashSkillUseMult,

        fWeaponBlockSkillUseBase,

        fWeaponBlockSkillUseMult,

        fWeaponClutterKnockBipedScale,

        fWeaponClutterKnockMaxWeaponMass,

        fWeaponClutterKnockMinClutterMass,

        fWeaponClutterKnockMult,

        fWeaponConditionCriticalChanceMult,

        fWeaponConditionJam,

        fWeaponConditionRateOfFire,

        fWeaponConditionReloadJam,

        fWeaponConditionSpread,

        fWeaponTwoHandedAnimationSpeedMult,

        fWeatherFlashAmbient,

        fWeatherFlashDirectional,

        fWeatherFlashDuration,

        fWeatherTransAccel,

        fWeatherTransMax,

        fWeatherTransMin,

        fWortFailSkillUseMagnitude,

        fWortStrMult,

        fWortalchmult,

        fWortcraftChanceIntDenom,

        fWortcraftChanceLuckDenom,

        fWortcraftStrChanceDenom,

        fWortcraftStrCostDenom,

        fXPLevelUpBase,

        fXPLevelUpMult,

        fXPPerSkillRank,

        fZKeyComplexHelperMinDistance,

        fZKeyComplexHelperScale,

        fZKeyComplexHelperWeightMax,

        fZKeyComplexHelperWeightMin,

        fZKeyHeavyWeight,

        fZKeyMaxContactDistance,

        fZKeyMaxContactMassRatio,

        fZKeyMaxForce,

        fZKeyMaxForceScaleHigh,

        fZKeyMaxForceScaleLow,

        fZKeyMaxForceWeightHigh,

        fZKeyMaxForceWeightLow,

        fZKeyObjectDamping,

        fZKeySpringDamping,

        fZKeySpringElasticity,

        fmodifiedTargetAttackRange
    }


    public enum IntSetting {


        iAICombatMaxAllySummonCount,

        iAICombatMinDetection,

        iAICombatRestoreHealthPercentage,

        iAICombatRestoreMagickaPercentage,

        iAIFleeMaxHitCount,

        iAIMaxSocialDistanceToTriggerEvent,

        iAINPCRacePowerChance,

        iAINumberActorsComplexScene,

        iAINumberDaysToStayBribed,

        iAINumberDaysToStayIntimidated,

        iAISocialDistanceToTriggerEvent,

        iActivatePickLength,

        iActorKeepTurnDegree,

        iActorLuckSkillBase,

        iActorTorsoMaxRotation,

        iAimingNumIterations,

        iAlertAgressionMin,

        iAllowAlchemyDuringCombat,

        iAllowRechargeDuringCombat,

        iAllowRepairDuringCombat,

        iAllyHitCombatAllowed,

        iAllyHitNonCombatAllowed,

        iArmorBaseSkill,

        iArmorDamageBootsChance,

        iArmorDamageCuirassChance,

        iArmorDamageGauntletsChance,

        iArmorDamageGreavesChance,

        iArmorDamageHelmChance,

        iArmorDamageShieldChance,

        iArrestOnSightNonViolent,

        iArrestOnSightViolent,

        iArrowInventoryChance,

        iArrowMaxCount,

        iAttackOnSightNonViolent,

        iAttackOnSightViolent,

        iAttractModeIdleTime,

        iAvoidHurtingNonTargetsResponsibility,

        iBallisticProjectilePathPickSegments,

        iBloodSplatterMaxCount,

        iBoneLODDistMult,

        iClassAcrobat,

        iClassAgent,

        iClassArcher,

        iClassAssassin,

        iClassBarbarian,

        iClassBard,

        iClassBattlemage,

        iClassCharactergenClass,

        iClassCrusader,

        iClassHealer,

        iClassKnight,

        iClassMage,

        iClassMonk,

        iClassNightblade,

        iClassPilgrim,

        iClassPriest,

        iClassRogue,

        iClassScout,

        iClassSorcerer,

        iClassSpellsword,

        iClassThief,

        iClassWarrior,

        iClassWitchhunter,

        iCombatAimMaxIterations,

        iCombatCastDrainMinimumValue,

        iCombatCrippledTorsoHitStaggerChance,

        iCombatDismemberPartChance,

        iCombatExplodePartChance,

        iCombatFlankingAngleOffsetCount,

        iCombatFlankingAngleOffsetGoalCount,

        iCombatFlankingDirectionOffsetCount,

        iCombatHighPriorityModifier,

        iCombatHoverLocationCount,

        iCombatSearchDoorFailureMax,

        iCombatStealthPointDetectionThreshold,

        iCombatStealthPointSneakDetectionThreshold,

        iCombatTargetLocationCount,

        iCombatTargetPlayerSoftCap,

        iCombatUnloadedActorLastSeenTimeLimit,

        iCommonSoulActorLevel,

        iCrimeAlarmLowRecDistance,

        iCrimeAlarmRecDistance,

        iCrimeCommentNumber,

        iCrimeDaysInPrisonMod,

        iCrimeEnemyCoolDownTimer,

        iCrimeFavorBaseValue,

        iCrimeGoldAttack,

        iCrimeGoldEscape,

        iCrimeGoldMinValue,

        iCrimeGoldMurder,

        iCrimeGoldPickpocket,

        iCrimeGoldStealHorse,

        iCrimeGoldTrespass,

        iCrimeGoldWerewolf,

        iCrimeMaxNumberofDaysinJail,

        iCrimeRegardBaseValue,

        iCrimeValueAttackValue,

        iCurrentTargetBonus,

        iDeathDropWeaponChance,

        iDebrisMaxCount,

        iDetectEventLightLevelExterior,

        iDetectEventLightLevelInterior,

        iDetectionHighNumPicks,

        iDialogueDispositionFriendValue,

        iDismemberBloodDecalCount,

        iDispKaramMax,

        iDistancetoAttackedTarget,

        iFallLegDamageChance,

        iFavorAllyValue,

        iFavorConfidantValue,

        iFavorFriendValue,

        iFavorLoverValue,

        iFavorPointsRestore,

        iFriendHitCombatAllowed,

        iFriendHitNonCombatAllowed,

        iGameplayiSpeakingEmotionDeltaChange,

        iGameplayiSpeakingEmotionListenValue,

        iGrandSoulActorLevel,

        iGreaterSoulActorLevel,

        iGuardWarnings,

        iHairColor,

        iHorseTurnDegreesPerSecond,

        iHorseTurnDegreesRampUpPerSecond,

        iInventoryAskQuantityAt,

        iIsInSneak,

        iKarmaMax,

        iKarmaMin,

        iKillCamLevelOffset,

        iLargeProjectilePickCount,

        iLastHDRSetting,

        iLesserSoulActorLevel,

        iLevelUpReminder,

        iLightLevelExteriorMod,

        iLightLevelInteriorMod,

        iLightLevelMax,

        iLocation,

        iLowLevelNPCMaxLevel,

        iMagicGuideWaypoints,

        iMagicLightMaxCount,

        iMapMarkerFadeStartDistance,

        iMapMarkerRevealDistance,

        iMapMarkerVisibleDistance,

        iMasserSize,

        iMaxCharacterLevel,

        iMaxPlayerRunes,

        iMaxQuestObjectives,

        iMaxSummonedCreatures,

        iMessCrippledLimbExplodeBonus,

        iMessIntactLimbDismemberChance,

        iMessIntactLimbExplodeBonus,

        iMessTargetedLimbExplodeBonus,

        iMessTorsoExplodeChance,

        iMinClipSizeToAddReloadDelay,

        iMineDisarmExperience,

        iMoodFaceValue,

        iNPCBasePerLevelHealthMult,

        iNumExplosionDecalCDPoint,

        iNumberActorsAllowedToFollowPlayer,

        iNumberActorsGoThroughLoadDoorInCombat,

        iNumberActorsInCombatPlayer,

        iNumberGuardsCrimeResponse,

        iPCStartSpellSkillLevel,

        iPerkAttackDisarmChance,

        iPerkBlockDisarmChance,

        iPerkBlockStaggerChance,

        iPerkHandToHandBlockRecoilChance,

        iPerkHeavyArmorJumpSum,

        iPerkHeavyArmorSinkSum,

        iPerkLightArmorMasterMinSum,

        iPerkMarksmanKnockdownChance,

        iPerkMarksmanParalyzeChance,

        iPersuasionAngleMax,

        iPersuasionAngleMin,

        iPersuasionBribeCrime,

        iPersuasionBribeGold,

        iPersuasionBribeRefuse,

        iPersuasionBribeScale,

        iPersuasionDemandDisposition,

        iPersuasionDemandGold,

        iPersuasionDemandRefuse,

        iPersuasionDemandScale,

        iPersuasionInner,

        iPersuasionMiddle,

        iPersuasionOuter,

        iPersuasionPower,

        iPickPocketWarnings,

        iPlayerCustomClass,

        iPlayerHealthHeartbeatFadeMS,

        iProjectileMaxRefCount,

        iProjectileMineShooterCanTrigger,

        iQuestReminderPipboyDisabledTime,

        iRelationshipAcquaintanceValue,

        iRelationshipAllyValue,

        iRelationshipArchnemesisValue,

        iRelationshipConfidantValue,

        iRelationshipEnemyValue,

        iRelationshipFoeValue,

        iRelationshipFriendValue,

        iRelationshipLoverValue,

        iRelationshipRivalValue,

        iRemoveExcessDeadComplexCount,

        iRemoveExcessDeadComplexTotalActorCount,

        iRemoveExcessDeadCount,

        iRemoveExcessDeadTotalActorCount,

        iSecondsToSleepPerUpdate,

        iSecundaSize,

        iShockBranchNumBolts,

        iShockBranchSegmentsPerBolt,

        iShockDebug,

        iShockNumBolts,

        iShockSegmentsPerBolt,

        iShockSubSegments,

        iSize,

        iSkillPointsTagSkillMult,

        iSkillUsageSneakFullDetection,

        iSkillUsageSneakMinDetection,

        iSneakSkillUseDistance,

        iSoundLevelLoud,

        iSoundLevelNormal,

        iSoundLevelSilent,

        iSoundLevelVeryLoud,

        iStaggerAttackChance,

        iStealWarnings,

        iTrainingExpertCost,

        iTrainingExpertSkill,

        iTrainingJourneymanCost,

        iTrainingJourneymanSkill,

        iTrainingMasterCost,

        iTrainingMasterSkill,

        iTrainingNumAllowedPerLevel,

        iTrespassWarnings,

        iVATSCameraHitDist,

        iVATSConcentratedFireBonus,

        iVATSStrangerMaxHP,

        iVoicePointsDefault,

        iWeaponCriticalHitDropChance,

        iXPBase,

        iXPBumpBase,

        iXPDeathRewardHealthThreshold,

        iXPLevelHackComputerAverage,

        iXPLevelHackComputerEasy,

        iXPLevelHackComputerHard,

        iXPLevelHackComputerVeryEasy,

        iXPLevelHackComputerVeryHard,

        iXPLevelKillCreatureAverage,

        iXPLevelKillCreatureEasy,

        iXPLevelKillCreatureHard,

        iXPLevelKillCreatureVeryEasy,

        iXPLevelKillCreatureVeryHard,

        iXPLevelKillNPCAverage,

        iXPLevelKillNPCEasy,

        iXPLevelKillNPCHard,

        iXPLevelKillNPCVeryEasy,

        iXPLevelKillNPCVeryHard,

        iXPLevelPickLockAverage,

        iXPLevelPickLockEasy,

        iXPLevelPickLockHard,

        iXPLevelPickLockVeryEasy,

        iXPLevelPickLockVeryHard,

        iXPLevelSpeechChallengeAverage,

        iXPLevelSpeechChallengeEasy,

        iXPLevelSpeechChallengeHard,

        iXPLevelSpeechChallengeVeryEasy,

        iXPLevelSpeechChallengeVeryHard,

        iXPRewardDiscoverMapMarker,

        iXPRewardDiscoverSecretArea,

        iXPRewardHackComputer,

        iXPRewardHackComputerAverage,

        iXPRewardHackComputerEasy,

        iXPRewardHackComputerHard,

        iXPRewardHackComputerVeryEasy,

        iXPRewardHackComputerVeryHard,

        iXPRewardKillNPCAverage,

        iXPRewardKillNPCEasy,

        iXPRewardKillNPCHard,

        iXPRewardKillNPCVeryEasy,

        iXPRewardKillNPCVeryHard,

        iXPRewardKillOpponent,

        iXPRewardKillOpponentAverage,

        iXPRewardKillOpponentEasy,

        iXPRewardKillOpponentHard,

        iXPRewardKillOpponentVeryEasy,

        iXPRewardKillOpponentVeryHard,

        iXPRewardPickLock,

        iXPRewardPickLockAverage,

        iXPRewardPickLockEasy,

        iXPRewardPickLockHard,

        iXPRewardPickLockVeryEasy,

        iXPRewardPickLockVeryHard,

        iXPRewardSpeechChallengeAverage,

        iXPRewardSpeechChallengeEasy,

        iXPRewardSpeechChallengeHard,

        iXPRewardSpeechChallengeVeryEasy,

        iXPRewardSpeechChallengeVeryHard,

        iHoursToRespawnCell,

        iHoursToRespawnCellCleared,

        iDaysToRespawnVendor,

        iHoursToClearCorpses,

        iMaxAttachedArrows
    }


    public enum StringSetting {


        sAbortText,

        sAccept,

        sActionMapping,

        sActionPointsAttack,

        sActionPointsCrouch,

        sActionPointsHeal,

        sActionPointsPunch,

        sActionPointsReload,

        sActionPointsStand,

        sActionPointsSwitchWeapon,

        sActionPointsToggleWeaponDrawn,

        sActivate,

        sActivateCreatureCalmed,

        sActivateNPCCalmed,

        sActivationChoiceMessage,

        sActiveEffects,

        sActiveMineDescription,

        sActorFade,

        sAdd,

        sAddCrimeGold,

        sAddItemtoInventory,

        sAddItemtoSpellList,

        sAddedEffects,

        sAddedNote,

        sAlchemy,

        sAlchemyMenuDescription,

        sAllItems,

        sAllegiance,

        sAlreadyKnown,

        sAlreadyPlacedMine,

        sAlteration,

        sAmber,

        sAnimationCanNotEquipArmor,

        sAnimationCanNotEquipWeapon,

        sAnimationCanNotUnequip,

        sApparel,

        sAreaText,

        sArmor,

        sArmorEnchantments,

        sArmorRating,

        sArmorSmithing,

        sAttack,

        sAttributeDamaged,

        sAttributeDrained,

        sAttributesCount,

        sAttributesTitle,

        sAutoLoading,

        sAutoSaveDisabledDueToLackOfSpace,

        sAutoSaving,

        sAutoSavingLong,

        sAutosaveAbbrev,

        sBack,

        sBasePath,

        sBleedingOutMessage,

        sBloodParticleDefault,

        sBloodParticleMeleeDefault,

        sBloodSplatterAlpha,

        sBloodSplatterColor,

        sBloodSplatterFlare,

        sBloodTextureDefault,

        sBooks,

        sBounty,

        sBountyStatString,

        sBrightness,

        sBroken,

        sButton,

        sButtonLocked,

        sCameraPitch,

        sCanNotEquipWornEnchantment,

        sCanNotReadBook,

        sCanNotTrainAnymore,

        sCanNotTrainHigher,

        sCancel,

        sCannotCastShout,

        sCantChangeResolution,

        sCantEquipBrokenItem,

        sCantEquipGeneric,

        sCantEquipPowerArmor,

        sCantHotkeyItem,

        sCantQuickLoad,

        sCantQuickSave,

        sCantRemoveWornItem,

        sCantRepairPastMax,

        sCantSaveNow,

        sCantUnequipGeneric,

        sChangeItemSelection,

        sCharGenControlsDisabled,

        sChemsAddicted,

        sChemsWithdrawal,

        sChemsWornOff,

        sChoose,

        sChooseSoulGem,

        sClearSelections,

        sCleared,

        sClose,

        sCombatCannotActivate,

        sConfirmAttribute,

        sConfirmContinue,

        sConfirmDelete,

        sConfirmDisenchant,

        sConfirmLoad,

        sConfirmNew,

        sConfirmSave,

        sConfirmSpendSoul,

        sConfirmWarning,

        sConjuration,

        sConstructibleMenuConfirm,

        sConstructibleMenuDescription,

        sContainerItemsTitle,

        sContainerPlaceChance,

        sContainerStealChance,

        sContinue,

        sContinueText,

        sContractedDisease,

        sControllerOption,

        sCopyProtectionMessage,

        sCopyProtectionTitle,

        sCorruptContentMessage,

        sCraft,

        sCreate,

        sCreated,

        sCreatedPoisonNamePrefix,

        sCreatedPotionNamePrefix,

        sCriticalStrike,

        sCrosshair,

        sCurrentLocation,

        sCurrentObjective,

        sCursorFilename,

        sDaedric,

        sDamage,

        sDefaultMessage,

        sDefaultPlayerName,

        sDeleteSaveGame,

        sDeleteSuccessful,

        sDestruction,

        sDevice,

        sDeviceRemoved,

        sDialogSubtitles,

        sDifficulty,

        sDisableHelp,

        sDisableXBoxController,

        sDiscoveredEffects,

        sDiscoveredIngredientEffectEating,

        sDiscoveredText,

        sDisenchant,

        sDismemberParticleDefault,

        sDismemberRobotParticleDefault,

        sDone,

        sDownloadsAvailable,

        sDownloadsNotAvail,

        sDragon,

        sDragonSoulAcquired,

        sDraugr,

        sDropEquippedItemWarning,

        sDropQuestItemWarning,

        sDungeonCleared,

        sDurationText,

        sDwarven,

        sEbony,

        sEffectAlreadyAdded,

        sEffectsListDisplayHour,

        sEffectsListDisplayHours,

        sEffectsListDisplayMin,

        sEffectsListDisplayMins,

        sEffectsListDisplaySec,

        sEffectsListDisplaySecs,

        sEffectsVolume,

        sElven,

        sEmpty,

        sEnableHelp,

        sEnchantArmorIncompatible,

        sEnchantDeconstructMenuDescription,

        sEnchantInsufficientCharge,

        sEnchantItem,

        sEnchantMenuDescription,

        sEnchantMustChooseItems,

        sEnchanting,

        sEnchantment,

        sEnchantmentKnown,

        sEnchantmentsLearned,

        sEnterItemName,

        sEnterName,

        sEquipItemOnPlayer,

        sEssentialCharacterDown,

        sExit,

        sExitGameAffirm,

        sExplosionSplashParticles,

        sFailShouting,

        sFailSpendSoul,

        sFailedActivation,

        sFalmer,

        sFastTravelConfirm,

        sFastTravelNoTravelHealthDamage,

        sFavorites,

        sFileNotFound,

        sFindingContentMessage,

        sFirstPersonSkeleton,

        sFood,

        sFootstepsVolume,

        sFor,

        sFullHealth,

        sFurnitureSleep,

        sGFWLive,

        sGeneralSubtitles,

        sGlass,

        sGold,

        sGotAwayWithStealing,

        sGrassFade,

        sHUDArmorRating,

        sHUDColor,

        sHUDCompleted,

        sHUDDamage,

        sHUDFailed,

        sHUDOpacity,

        sHUDStarted,

        sHairColor,

        sHarvest,

        sHealth,

        sHeavyArmorNoJump,

        sHeavyArmorSink,

        sHelp,

        sHide,

        sHigh,

        sHold,

        sIgnoreText,

        sIllusion,

        sImpactParticleConcreteDefault,

        sImpactParticleMetalDefault,

        sImpactParticleWoodDefault,

        sImperial,

        sImpossibleLock,

        sImprovement,

        sInaccessible,

        sIngredient,

        sIngredientFail,

        sIngredients,

        sInsufficientGoldToTrain,

        sInvalidPickpocket,

        sIron,

        sItem,

        sItemFade,

        sItemTooExpensive,

        sItemTooHeavy,

        sJewelry,

        sJunk,

        sKeyLocked,

        sKeys,

        sKnownEffects,

        sLackRequiredPerkToImproveMagical,

        sLackRequiredPerksToImprove,

        sLackRequiredSkillToImprove,

        sLackRequiredToCreate,

        sLackRequiredToImprove,

        sLarge,

        sLearn,

        sLearningEnchantments,

        sLeather,

        sLeaveMarker,

        sLevelAbbrev,

        sLevelProgress,

        sLevelUpAvailable,

        sLightFade,

        sLoadFromMainMenu,

        sLoadWhilePlaying,

        sLoading,

        sLoadingArea,

        sLoadingContentMessage,

        sLoadingLOD,

        sLockBroken,

        sLocked,

        sLockpickInsufficientPerks,

        sLostController,

        sLow,

        sMagicEffectNotApplied,

        sMagicEffectResisted,

        sMagicEnhanceWeaponNoWeapon,

        sMagicEnhanceWeaponWeaponEnchanted,

        sMagicGuideNoMarker,

        sMagicGuideNoPath,

        sMagicTelekinesisNoRecast,

        sMagnitudeIsLevelText,

        sMagnitudeText,

        sMainMenu,

        sMakeDefaults,

        sMapMarkerAdded,

        sMasterVolume,

        sMedium,

        sMenuDisplayAutosaveName,

        sMenuDisplayDayString,

        sMenuDisplayLevelString,

        sMenuDisplayNewSave,

        sMenuDisplayNoSaves,

        sMenuDisplayPlayTime,

        sMenuDisplayQuicksaveName,

        sMenuDisplaySave,

        sMenuDisplayShortXBoxSaveMessage,

        sMenuDisplayUnknownLocationString,

        sMenuDisplayXBoxSaveMessage,

        sMisc,

        sMiscConstantEffect,

        sMiscPlayerDeadLoadOption,

        sMiscPlayerDeadMenuOption,

        sMiscPlayerDeadMessage,

        sMiscQuestDescription,

        sMiscQuestName,

        sMiscUnknownEffect,

        sMissingImage,

        sMissingName,

        sMouseSensitivity,

        sMoveMarker,

        sMoveMarkerQuestion,

        sMultipleDragonSoulCount,

        sMusicVolume,

        sMustRestart,

        sName,

        sNeutral,

        sNewSave,

        sNext,

        sNo,

        sNoArrows,

        sNoChargeItems,

        sNoChildUse,

        sNoDeviceSelected,

        sNoEatQuestItem,

        sNoFastTravelAlarm,

        sNoFastTravelCell,

        sNoFastTravelCombat,

        sNoFastTravelDefault,

        sNoFastTravelHostileActorsNear,

        sNoFastTravelInAir,

        sNoFastTravelOverencumbered,

        sNoFastTravelScriptBlock,

        sNoFastTravelUndiscovered,

        sNoItemsToRepair,

        sNoJumpWarning,

        sNoKeyDropWarning,

        sNoLockPickIfCrimeAlert,

        sNoMoreFollowers,

        sNoPickPocketAgain,

        sNoProfileSelected,

        sNoRepairHostileActorsNear,

        sNoRepairInCombat,

        sNoRestart,

        sNoSaves,

        sNoSitOnOwnedFurniture,

        sNoSleepDefault,

        sNoSleepHostileActorsNear,

        sNoSleepInAir,

        sNoSleepInCell,

        sNoSleepInOwnedBed,

        sNoSleepTakingHealthDamage,

        sNoSleepTrespass,

        sNoSleepWarnToLeave,

        sNoSleepWhileAlarm,

        sNoSpareParts,

        sNoTalkFleeing,

        sNoTalkUnConscious,

        sNoText,

        sNoWaitDefault,

        sNoWaitHostileActorsNear,

        sNoWaitInAir,

        sNoWaitInCell,

        sNoWaitTakingHealthDamage,

        sNoWaitTrespass,

        sNoWaitWarnToLeave,

        sNoWaitWhileAlarm,

        sNone,

        sNormal,

        sNormalWeaponsResisted,

        sNotAllowedToUseAutoDoorsWhileonHorse,

        sNotEnoughRoomWarning,

        sNotEnoughVendorGold,

        sNumberAbbrev,

        sOKText,

        sObjectFade,

        sObjectInUse,

        sObjectLODFade,

        sOf,

        sOff,

        sOk,

        sOldDownloadsAvailable,

        sOn,

        sOpen,

        sOpenWithKey,

        sOpenedContainer,

        sOptional,

        sOr,

        sOrcish,

        sOutOfLockpicks,

        sOverEncumbered,

        sOwned,

        sPCControlsTextNone,

        sPCControlsTextPrefix,

        sPCControlsTriggerPrefix,

        sPCRelationshipNegativeChangeText,

        sPCRelationshipPositiveChangeText,

        sPauseText,

        sPickpocket,

        sPickpocketFail,

        sPipboyColor,

        sPlaceMarker,

        sPlaceMarkerUndiscovered,

        sPlayTime,

        sPlayerDisarmedMessage,

        sPlayerLeavingBorderRegion,

        sPlayerSetMarkerName,

        sPleaseStandBy,

        sPoisonAlreadyPoisonedMessage,

        sPoisonBowConfirmMessage,

        sPoisonConfirmMessage,

        sPoisonNoWeaponMessage,

        sPoisonUnableToPoison,

        sPoisoned,

        sPotionCreationFailed,

        sPotions,

        sPowers,

        sPressControl,

        sPrevious,

        sPreviousSelection,

        sPrisoner,

        sQuantity,

        sQuestAddedText,

        sQuestCompletedText,

        sQuestFailed,

        sQuestUpdatedText,

        sQuickLoading,

        sQuickSaving,

        sQuicksaveAbbrev,

        sQuitAlchemy,

        sQuitEnchanting,

        sRSMAge,

        sRSMBody,

        sRSMBrow,

        sRSMBrowForward,

        sRSMBrowHeight,

        sRSMBrowTypes,

        sRSMBrowWidth,

        sRSMCheekColor,

        sRSMCheekColorLower,

        sRSMCheekboneHeight,

        sRSMCheekboneWidth,

        sRSMChinColor,

        sRSMChinForward,

        sRSMChinLength,

        sRSMChinWidth,

        sRSMComplexion,

        sRSMComplexionColor,

        sRSMConfirm,

        sRSMConfirmDestruction,

        sRSMDirt,

        sRSMDirtColor,

        sRSMEyeColor,

        sRSMEyeDepth,

        sRSMEyeHeight,

        sRSMEyeSocketLowerColor,

        sRSMEyeSocketUpperColor,

        sRSMEyeTypes,

        sRSMEyeWidth,

        sRSMEyelinerColor,

        sRSMEyes,

        sRSMFace,

        sRSMFacialHairColorPresets,

        sRSMFacialHairPresets,

        sRSMForeheadColor,

        sRSMHair,

        sRSMHairColorPresets,

        sRSMHairPresets,

        sRSMHead,

        sRSMHeadPresets,

        sRSMJawForward,

        sRSMJawHeight,

        sRSMJawWidth,

        sRSMLaughLines,

        sRSMLipColor,

        sRSMMouth,

        sRSMMouthForward,

        sRSMMouthHeight,

        sRSMMouthTypes,

        sRSMName,

        sRSMNameWarning,

        sRSMNeckColor,

        sRSMNoseColor,

        sRSMNoseHeight,

        sRSMNoseLength,

        sRSMNoseTypes,

        sRSMPaint,

        sRSMPaintColor,

        sRSMRace,

        sRSMScars,

        sRSMSex,

        sRSMSkinColor,

        sRSMTone,

        sRSMWeight,

        sRadioSignalLost,

        sRadioStationDiscovered,

        sRadioVolume,

        sRangeText,

        sRanksText,

        sRead,

        sRemoteActivation,

        sRemove,

        sRemoveCrimeGold,

        sRemoveItemfromInventory,

        sRemoveMarker,

        sRenameItem,

        sRepair,

        sRepairAllItems,

        sRepairCost,

        sRepairItem,

        sRepairServicesTitle,

        sRepairSkill,

        sRepairSkillTooLow,

        sRequirements,

        sRequirementsText,

        sResetToDefaults,

        sResolution,

        sResource,

        sRestartBecauseContentRemoved,

        sRestartSignedOut,

        sRestartToUseNewContent,

        sRestartToUseProfileContent,

        sRestoration,

        sRetryText,

        sReturn,

        sRewardXP,

        sRewardXPIcon,

        sRide,

        sRumble,

        sSaveFailed,

        sSaveGameContentIsMissing,

        sSaveGameCorrupt,

        sSaveGameCorruptMenuMessage,

        sSaveGameDeviceError,

        sSaveGameIsCorrupt,

        sSaveGameNoLongerAvailable,

        sSaveGameNoMasterFilesFound,

        sSaveGameOldVersion,

        sSaveGameOutOfDiskSpace,

        sSaveNotAvailable,

        sSaveOnRest,

        sSaveOnTravel,

        sSaveOnWait,

        sSaveOverSaveGame,

        sSaveSuccessful,

        sSceneBlockingActorActivation,

        sScrollEquipped,

        sScrolls,

        sSearch,

        sSelect,

        sSelectItemToRepair,

        sSelfRange,

        sServeSentenceQuestion,

        sServeTimeQuestion,

        sSexFemale,

        sSexFemalePossessive,

        sSexFemalePronoun,

        sSexMale,

        sSexMalePossessive,

        sSexMalePronoun,

        sShadowFade,

        sShoutAdded,

        sShouts,

        sSingleDragonSoulCount,

        sSit,

        sSkillIncreased,

        sSkillIncreasedNum,

        sSkillsCount,

        sSkillsTitle,

        sSmall,

        sSmithingConfirm,

        sSmithingMenuDescription,

        sSneakAttack,

        sSneakCaution,

        sSneakDanger,

        sSneakDetected,

        sSneakHidden,

        sSortMethod,

        sSoulCaptured,

        sSoulGem,

        sSoulGemTooSmall,

        sSoulGems,

        sSoulLevel,

        sSpace,

        sSpecularityFade,

        sSpeechChallengeFailure,

        sSpeechChallengeSuccess,

        sSpellAdded,

        sSplashParticles,

        sStatsMustSelectPerk,

        sStatsNextRank,

        sStatsPerkConfirm,

        sSteal,

        sStealFrom,

        sStealHorse,

        sSteel,

        sStormcloak,

        sStudded,

        sSuccessfulSneakAttackEnd,

        sSuccessfulSneakAttackMain,

        sTake,

        sTakeAll,

        sTalk,

        sTargetRange,

        sTeammateCantGiveOutfit,

        sTeammateCantTakeOutfit,

        sTeammateOverencumbered,

        sTestFile,

        sTextureSize,

        sTo,

        sTouchRange,

        sTraitsCount,

        sTraitsTitle,

        sTreeLODFade,

        sTweenDisabledMessage,

        sUIMistMenu,

        sUnequipItemOnPlayer,

        sUnlock,

        sUse,

        sVATSMessageLowAP,

        sVATSMessageNoAmmo,

        sVATSMessageZeroChance,

        sVDSGManual,

        sVDSGPlate,

        sValue,

        sVatsAimed,

        sVatsAiming,

        sVatsBodyPart,

        sVatsSelect,

        sVatsTarget,

        sVideoChange,

        sViewDistance,

        sVoiceVolume,

        sWaitHere,

        sWeaponBreak,

        sWeaponEnchantments,

        sWeaponSmithing,

        sWeapons,

        sWeight,

        sWhite,

        sWitnessKilled,

        sWood,

        sXSensitivity,

        sYSensitivity,

        sYes,

        sYesRestart,

        sYesText,

        sYesToAllText,

        sYour
    }

    static final class DATA extends SubRecord {

        final SubData DATA = new SubData("DATA");
        final SubStringPointer DATAs = new SubStringPointer("DATA", SubStringPointer.Files.STRINGS);
        private GMSTType GMSTtype;

        DATA() {
            super();
            DATAs.forceExport = true;
        }

        @Override
        SubRecord getNew(String type) {
            return new DATA();
        }

        @Override
        void export(ModExporter out) throws IOException {
            switch (GMSTtype) {
                case String:
                    DATAs.export(out);
                    break;
                default:
                    DATA.export(out);
            }
        }

        @Override
        void parseData(LImport in, Mod srcMod) throws BadRecord, DataFormatException, BadParameter {
            switch (GMSTtype) {
                case String:
                    DATAs.parseData(in, srcMod);
                    break;
                default:
                    DATA.parseData(in, srcMod);
            }
        }

        @Override
        int getContentLength(boolean isStringTabled) {
            switch (GMSTtype) {
                case String:
                    return DATAs.getContentLength(isStringTabled);
                default:
                    return DATA.getContentLength(isStringTabled);
            }
        }

        @Override
        ArrayList<String> getTypes() {
            return Record.getTypeList("DATA");
        }
    }
}
