package me.client.module.combat.backtrackv2;


import me.client.module.combat.backtrackv2.utils.DelayedPacket;
import me.client.module.combat.backtrackv2.utils.DelayedPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.network.Packet;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.network.play.server.*;

public class PingChanger {
   public static int additionalping;
   public static boolean enabled;

   public static void setEnabled(boolean enabled) {
      PingChanger.enabled = enabled;
   }

   public static boolean isEnabled() {
      return enabled;
   }

   public PingChanger() {
      enabled = false;
      additionalping = 0;
   }

   public static void SpoofPacket(Packet p) {
      PingChangerMod.packets.add(new DelayedPacket(p, System.currentTimeMillis()));
   }

   public static boolean ShouldPacketBeSpoofed(Packet packet) {

      if (IsPlayClientPacket(packet)) {
      return PingChanger.enabled;
      }
      return false;
   }
   public static double getAdditionalPing() {
      return PingChanger.additionalping;
   }

   public static boolean isSpoofing() {
      return PingChanger.enabled;
   }
   public static void setAdditionalPing(int i) {
      PingChanger.additionalping = i;
      return;
   }
   public static int getEstimatedPing() {
      return PingChanger.additionalping+(isSpoofing() ? (getServerPing()-PingChanger.additionalping) : getServerPing());
   }

   public static int getServerPing() {
      NetworkPlayerInfo playerInfo = Minecraft.getMinecraft().getNetHandler().getPlayerInfo(Minecraft.getMinecraft().thePlayer.getUniqueID());
      return playerInfo.getResponseTime();
   }



   public static boolean IsPlayClientPacket(Packet packet) {

      return packet instanceof S0EPacketSpawnObject || packet instanceof S11PacketSpawnExperienceOrb || packet instanceof S2CPacketSpawnGlobalEntity || packet instanceof
              S0FPacketSpawnMob || packet instanceof S3BPacketScoreboardObjective || packet instanceof S10PacketSpawnPainting || packet instanceof S0CPacketSpawnPlayer || packet instanceof S0BPacketAnimation || packet instanceof
              S37PacketStatistics || packet instanceof S25PacketBlockBreakAnim || packet instanceof S36PacketSignEditorOpen || packet instanceof S35PacketUpdateTileEntity || packet instanceof S24PacketBlockAction || packet instanceof
              S23PacketBlockChange || packet instanceof S02PacketChat || packet instanceof S3APacketTabComplete || packet instanceof S22PacketMultiBlockChange || packet instanceof S34PacketMaps || packet instanceof S32PacketConfirmTransaction || packet instanceof
              S2EPacketCloseWindow || packet instanceof S30PacketWindowItems || packet instanceof S2DPacketOpenWindow || packet instanceof S31PacketWindowProperty || packet instanceof S2FPacketSetSlot || packet instanceof S3FPacketCustomPayload || packet instanceof S0APacketUseBed || packet instanceof S19PacketEntityStatus || packet instanceof S1BPacketEntityAttach || packet instanceof S27PacketExplosion || packet instanceof S2BPacketChangeGameState || packet instanceof
              S00PacketKeepAlive || packet instanceof S21PacketChunkData || packet instanceof S26PacketMapChunkBulk || packet instanceof S28PacketEffect || packet instanceof S14PacketEntity || packet instanceof S08PacketPlayerPosLook || packet instanceof
              S2APacketParticles || packet instanceof S39PacketPlayerAbilities || packet instanceof S38PacketPlayerListItem || packet instanceof S13PacketDestroyEntities || packet instanceof S1EPacketRemoveEntityEffect || packet instanceof S07PacketRespawn || packet instanceof
              S19PacketEntityHeadLook || packet instanceof S09PacketHeldItemChange || packet instanceof S3DPacketDisplayScoreboard || packet instanceof S1CPacketEntityMetadata || packet instanceof S12PacketEntityVelocity || packet instanceof S04PacketEntityEquipment || packet instanceof
              S1FPacketSetExperience || packet instanceof S06PacketUpdateHealth || packet instanceof S3EPacketTeams || packet instanceof S3CPacketUpdateScore || packet instanceof S05PacketSpawnPosition || packet instanceof S03PacketTimeUpdate || packet instanceof S33PacketUpdateSign || packet instanceof
              S29PacketSoundEffect || packet instanceof S0DPacketCollectItem || packet instanceof S18PacketEntityTeleport || packet instanceof S20PacketEntityProperties || packet instanceof S1DPacketEntityEffect || packet instanceof S42PacketCombatEvent || packet instanceof
              S41PacketServerDifficulty || packet instanceof S43PacketCamera || packet instanceof S44PacketWorldBorder || packet instanceof S45PacketTitle || packet instanceof S46PacketSetCompressionLevel || packet instanceof S47PacketPlayerListHeaderFooter || packet instanceof
              S48PacketResourcePackSend || packet instanceof S49PacketUpdateEntityNBT;
   }

}
