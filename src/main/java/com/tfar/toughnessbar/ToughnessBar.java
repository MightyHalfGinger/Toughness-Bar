package com.tfar.toughnessbar;

import com.google.common.collect.Lists;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static com.tfar.toughnessbar.ToughnessBar.MOD_ID;

@Mod(value = MOD_ID)
public class ToughnessBar {
  public static final String MOD_ID = "toughnessbar";

  public ToughnessBar() {
    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ToughnessBarConfig.CLIENT_SPEC);
  }

  public void setup(FMLClientSetupEvent event) {
    MinecraftForge.EVENT_BUS.register(new EventHandlerClient());
  }

  public static class ToughnessBarConfig {

    public static final ClientConfig CLIENT;
    public static final ForgeConfigSpec CLIENT_SPEC;

    static {
      final Pair<ClientConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ClientConfig::new);
      CLIENT_SPEC = specPair.getRight();
      CLIENT = specPair.getLeft();
    }

    public static class ClientConfig {
      public static ForgeConfigSpec.BooleanValue empty;
      public static ForgeConfigSpec.ConfigValue<List<? extends String>> colorValues;

      ClientConfig(ForgeConfigSpec.Builder builder) {
        builder.push("general");
        colorValues = builder
                .comment("Toughness Bar Icon Colors")
                .translation("text.toughnessbar.config.colorvalues")
                .defineList("color values", Lists.newArrayList("#FFFFFF", "#FF5500", "#FFC747", "#27FFE3", "#00FF00", "#7F00FF"), String.class::isInstance);
        empty = builder
                .comment("Show empty armor toughness icons?")
                .translation("text.toughnessbar.config.showemptyarmortoughnessicons")
                .define("Show empty icons", false);
        builder.pop();
      }
    }
  }
}