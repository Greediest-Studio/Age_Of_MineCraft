package net.minecraft.AgeOfMinecraft.models;

import net.minecraft.AgeOfMinecraft.entity.tame.cameos.EntityGasterBlaster;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelGasterBlaster extends ModelBase {
  private final ModelRenderer gasterblaster;
  
  private final ModelRenderer mouth;
  
  private final ModelRenderer leftmouth;
  
  private final ModelRenderer rightmouth;
  
  private final ModelRenderer bone2;
  
  private final ModelRenderer bone43;
  
  private final ModelRenderer bone44;
  
  private final ModelRenderer bone45;
  
  private final ModelRenderer bone5;
  
  private final ModelRenderer bone6;
  
  private final ModelRenderer bone7;
  
  private final ModelRenderer bone8;
  
  private final ModelRenderer bone9;
  
  private final ModelRenderer bone10;
  
  private final ModelRenderer bone14;
  
  private final ModelRenderer bone15;
  
  private final ModelRenderer bone16;
  
  private final ModelRenderer bone;
  
  private final ModelRenderer bone3;
  
  private final ModelRenderer bone27;
  
  private final ModelRenderer bone135;
  
  private final ModelRenderer bone26;
  
  private final ModelRenderer bone23;
  
  private final ModelRenderer bone33;
  
  private final ModelRenderer bone34;
  
  private final ModelRenderer bone35;
  
  private final ModelRenderer bone36;
  
  private final ModelRenderer bone37;
  
  private final ModelRenderer bone50;
  
  private final ModelRenderer bone49;
  
  private final ModelRenderer bone24;
  
  private final ModelRenderer bone25;
  
  private final ModelRenderer bone4;
  
  private final ModelRenderer bone11;
  
  private final ModelRenderer bone12;
  
  private final ModelRenderer bone13;
  
  private final ModelRenderer bone17;
  
  private final ModelRenderer bone18;
  
  private final ModelRenderer bone19;
  
  private final ModelRenderer bone20;
  
  private final ModelRenderer bone21;
  
  private final ModelRenderer bone22;
  
  private final ModelRenderer bone28;
  
  private final ModelRenderer bone29;
  
  private final ModelRenderer bone30;
  
  private final ModelRenderer bone31;
  
  private final ModelRenderer bone32;
  
  private final ModelRenderer bone38;
  
  private final ModelRenderer bone39;
  
  private final ModelRenderer bone40;
  
  private final ModelRenderer bone41;
  
  private final ModelRenderer bone42;
  
  private final ModelRenderer bone46;
  
  private final ModelRenderer bone47;
  
  private final ModelRenderer bone48;
  
  private final ModelRenderer bone51;
  
  private final ModelRenderer bone52;
  
  private final ModelRenderer bone136;
  
  private final ModelRenderer bone137;
  
  private final ModelRenderer bone138;
  
  private final ModelRenderer head;
  
  private final ModelRenderer lefthead;
  
  private final ModelRenderer bone53;
  
  private final ModelRenderer bone57;
  
  private final ModelRenderer bone86;
  
  private final ModelRenderer bone83;
  
  private final ModelRenderer bone85;
  
  private final ModelRenderer bone89;
  
  private final ModelRenderer bone60;
  
  private final ModelRenderer bone61;
  
  private final ModelRenderer bone64;
  
  private final ModelRenderer bone68;
  
  private final ModelRenderer bone67;
  
  private final ModelRenderer bone62;
  
  private final ModelRenderer bone63;
  
  private final ModelRenderer bone56;
  
  private final ModelRenderer bone54;
  
  private final ModelRenderer bone58;
  
  private final ModelRenderer bone65;
  
  private final ModelRenderer bone98;
  
  private final ModelRenderer bone99;
  
  private final ModelRenderer bone109;
  
  private final ModelRenderer bone110;
  
  private final ModelRenderer bone111;
  
  private final ModelRenderer bone106;
  
  private final ModelRenderer bone66;
  
  private final ModelRenderer bone100;
  
  private final ModelRenderer bone69;
  
  private final ModelRenderer bone101;
  
  private final ModelRenderer bone105;
  
  private final ModelRenderer bone108;
  
  private final ModelRenderer bone112;
  
  private final ModelRenderer bone107;
  
  private final ModelRenderer bone102;
  
  private final ModelRenderer bone103;
  
  private final ModelRenderer bone91;
  
  private final ModelRenderer bone92;
  
  private final ModelRenderer bone97;
  
  private final ModelRenderer bone104;
  
  private final ModelRenderer bone55;
  
  private final ModelRenderer bone71;
  
  private final ModelRenderer bone59;
  
  private final ModelRenderer bone70;
  
  private final ModelRenderer eye;
  
  private final ModelRenderer righthead;
  
  private final ModelRenderer bone72;
  
  private final ModelRenderer bone73;
  
  private final ModelRenderer bone74;
  
  private final ModelRenderer bone75;
  
  private final ModelRenderer bone76;
  
  private final ModelRenderer bone77;
  
  private final ModelRenderer bone78;
  
  private final ModelRenderer bone79;
  
  private final ModelRenderer bone80;
  
  private final ModelRenderer bone81;
  
  private final ModelRenderer bone82;
  
  private final ModelRenderer bone84;
  
  private final ModelRenderer bone87;
  
  private final ModelRenderer bone88;
  
  private final ModelRenderer bone90;
  
  private final ModelRenderer bone93;
  
  private final ModelRenderer bone94;
  
  private final ModelRenderer bone95;
  
  private final ModelRenderer bone96;
  
  private final ModelRenderer bone113;
  
  private final ModelRenderer bone114;
  
  private final ModelRenderer bone115;
  
  private final ModelRenderer bone116;
  
  private final ModelRenderer bone117;
  
  private final ModelRenderer bone118;
  
  private final ModelRenderer bone119;
  
  private final ModelRenderer bone120;
  
  private final ModelRenderer bone121;
  
  private final ModelRenderer bone122;
  
  private final ModelRenderer bone123;
  
  private final ModelRenderer bone124;
  
  private final ModelRenderer bone125;
  
  private final ModelRenderer bone126;
  
  private final ModelRenderer bone127;
  
  private final ModelRenderer bone128;
  
  private final ModelRenderer bone129;
  
  private final ModelRenderer bone130;
  
  private final ModelRenderer bone131;
  
  private final ModelRenderer bone132;
  
  private final ModelRenderer bone133;
  
  private final ModelRenderer bone134;
  
  private final ModelRenderer eye2;
  
  public ModelGasterBlaster() {
    this.field_78090_t = 128;
    this.field_78089_u = 128;
    this.gasterblaster = new ModelRenderer(this);
    this.gasterblaster.func_78793_a(0.0F, 24.0F, 0.0F);
    this.mouth = new ModelRenderer(this);
    this.mouth.func_78793_a(0.0F, 0.0F, 8.0F);
    this.gasterblaster.func_78792_a(this.mouth);
    this.leftmouth = new ModelRenderer(this);
    this.leftmouth.func_78793_a(8.0F, -8.0F, -3.0F);
    this.mouth.func_78792_a(this.leftmouth);
    setRotationAngle(this.leftmouth, 0.0873F, 0.3491F, 0.0F);
    this.leftmouth.field_78804_l.add(new ModelBox(this.leftmouth, 32, 0, -0.9823F, 4.0118F, -15.665F, 1, 2, 11, 0.0F, false));
    this.bone2 = new ModelRenderer(this);
    this.bone2.func_78793_a(-0.2081F, 6.0118F, -4.759F);
    this.leftmouth.func_78792_a(this.bone2);
    setRotationAngle(this.bone2, 0.2618F, 0.0F, 0.0F);
    this.bone2.field_78804_l.add(new ModelBox(this.bone2, 0, 17, -0.7742F, -2.0F, 0.0941F, 1, 2, 6, 0.0F, false));
    this.bone43 = new ModelRenderer(this);
    this.bone43.func_78793_a(-0.2081F, 6.0118F, 0.241F);
    this.leftmouth.func_78792_a(this.bone43);
    setRotationAngle(this.bone43, -0.4363F, 0.0F, 0.0F);
    this.bone43.field_78804_l.add(new ModelBox(this.bone43, 55, 57, -0.7742F, -1.0F, -2.9059F, 1, 1, 7, 0.0F, false));
    this.bone44 = new ModelRenderer(this);
    this.bone44.func_78793_a(0.0F, -1.0F, 4.0F);
    this.bone43.func_78792_a(this.bone44);
    setRotationAngle(this.bone44, -0.3491F, 0.0F, 0.0F);
    this.bone44.field_78804_l.add(new ModelBox(this.bone44, 34, 57, -0.7742F, 0.0F, -6.9059F, 1, 1, 7, 0.0F, false));
    this.bone45 = new ModelRenderer(this);
    this.bone45.func_78793_a(0.0F, -1.0F, 4.0F);
    this.bone43.func_78792_a(this.bone45);
    setRotationAngle(this.bone45, -0.1745F, 0.0F, 0.0F);
    this.bone45.field_78804_l.add(new ModelBox(this.bone45, 56, 34, -0.7742F, 0.0F, -6.9059F, 1, 1, 7, 0.0F, false));
    this.bone5 = new ModelRenderer(this);
    this.bone5.func_78793_a(4.7919F, 7.0118F, 1.241F);
    this.leftmouth.func_78792_a(this.bone5);
    this.bone5.field_78804_l.add(new ModelBox(this.bone5, 59, 45, -5.7742F, -4.0F, -15.9659F, 1, 2, 1, 0.0F, false));
    this.bone6 = new ModelRenderer(this);
    this.bone6.func_78793_a(-5.0F, -4.0F, -14.9659F);
    this.bone5.func_78792_a(this.bone6);
    setRotationAngle(this.bone6, 0.2618F, 0.0F, 0.0F);
    this.bone6.field_78804_l.add(new ModelBox(this.bone6, 58, 42, -0.7742F, 0.0F, -1.0F, 1, 2, 1, 0.0F, false));
    this.bone7 = new ModelRenderer(this);
    this.bone7.func_78793_a(-5.0F, -4.0F, -15.9659F);
    this.bone5.func_78792_a(this.bone7);
    setRotationAngle(this.bone7, -0.2618F, 0.0F, 0.0F);
    this.bone7.field_78804_l.add(new ModelBox(this.bone7, 58, 28, -0.7742F, 0.0F, 0.0F, 1, 2, 1, 0.0F, false));
    this.bone8 = new ModelRenderer(this);
    this.bone8.func_78793_a(4.7919F, 7.0118F, 4.241F);
    this.leftmouth.func_78792_a(this.bone8);
    this.bone8.field_78804_l.add(new ModelBox(this.bone8, 58, 25, -5.7742F, -4.0F, -15.9659F, 1, 2, 1, 0.0F, false));
    this.bone9 = new ModelRenderer(this);
    this.bone9.func_78793_a(-5.0F, -4.0F, -14.9659F);
    this.bone8.func_78792_a(this.bone9);
    setRotationAngle(this.bone9, 0.2618F, 0.0F, 0.0F);
    this.bone9.field_78804_l.add(new ModelBox(this.bone9, 58, 58, -0.7742F, 0.0F, -1.0F, 1, 2, 1, 0.0F, false));
    this.bone10 = new ModelRenderer(this);
    this.bone10.func_78793_a(-5.0F, -4.0F, -15.9659F);
    this.bone8.func_78792_a(this.bone10);
    setRotationAngle(this.bone10, -0.2618F, 0.0F, 0.0F);
    this.bone10.field_78804_l.add(new ModelBox(this.bone10, 37, 58, -0.7742F, 0.0F, 0.0F, 1, 2, 1, 0.0F, false));
    this.bone14 = new ModelRenderer(this);
    this.bone14.func_78793_a(4.7919F, 7.0118F, 8.241F);
    this.leftmouth.func_78792_a(this.bone14);
    this.bone14.field_78804_l.add(new ModelBox(this.bone14, 57, 52, -5.7742F, -4.0F, -15.9659F, 1, 2, 1, 0.0F, false));
    this.bone15 = new ModelRenderer(this);
    this.bone15.func_78793_a(-5.0F, -4.0F, -14.9659F);
    this.bone14.func_78792_a(this.bone15);
    setRotationAngle(this.bone15, 0.2618F, 0.0F, 0.0F);
    this.bone15.field_78804_l.add(new ModelBox(this.bone15, 57, 49, -0.7742F, 0.0F, -1.0F, 1, 2, 1, 0.0F, false));
    this.bone16 = new ModelRenderer(this);
    this.bone16.func_78793_a(-5.0F, -4.0F, -15.9659F);
    this.bone14.func_78792_a(this.bone16);
    setRotationAngle(this.bone16, -0.2618F, 0.0F, 0.0F);
    this.bone16.field_78804_l.add(new ModelBox(this.bone16, 57, 17, -0.7742F, 0.0F, 0.0F, 1, 2, 1, 0.0F, false));
    this.bone = new ModelRenderer(this);
    this.bone.func_78793_a(-1.9493F, 6.0118F, -16.725F);
    this.leftmouth.func_78792_a(this.bone);
    setRotationAngle(this.bone, 0.0F, -0.9599F, 0.0F);
    this.bone.field_78804_l.add(new ModelBox(this.bone, 0, 38, -1.0F, -2.0F, -1.0F, 3, 2, 1, 0.0F, false));
    this.bone.field_78804_l.add(new ModelBox(this.bone, 55, 60, -1.0F, -4.0F, -1.0F, 1, 2, 1, 0.0F, false));
    this.bone3 = new ModelRenderer(this);
    this.bone3.func_78793_a(0.0F, -4.0F, 0.0F);
    this.bone.func_78792_a(this.bone3);
    setRotationAngle(this.bone3, 0.0F, 0.0F, -0.3491F);
    this.bone3.field_78804_l.add(new ModelBox(this.bone3, 46, 49, -1.0F, 0.0F, -1.0F, 1, 3, 1, 0.0F, false));
    this.bone27 = new ModelRenderer(this);
    this.bone27.func_78793_a(-1.9493F, 6.0118F, -16.725F);
    this.leftmouth.func_78792_a(this.bone27);
    this.bone135 = new ModelRenderer(this);
    this.bone135.func_78793_a(-0.684F, 4.1638F, -6.1278F);
    this.leftmouth.func_78792_a(this.bone135);
    setRotationAngle(this.bone135, 0.1745F, 0.0F, 0.0F);
    this.bone26 = new ModelRenderer(this);
    this.bone26.func_78793_a(0.4759F, -2.152F, 3.4028F);
    this.bone135.func_78792_a(this.bone26);
    setRotationAngle(this.bone26, -0.6981F, 0.0F, 0.0F);
    this.bone26.field_78804_l.add(new ModelBox(this.bone26, 19, 47, -0.7742F, 0.0F, 0.0F, 1, 3, 1, 0.0F, false));
    this.bone23 = new ModelRenderer(this);
    this.bone23.func_78793_a(0.4759F, -2.152F, 3.4028F);
    this.bone135.func_78792_a(this.bone23);
    setRotationAngle(this.bone23, -1.0472F, 0.0F, 0.0F);
    this.bone23.field_78804_l.add(new ModelBox(this.bone23, 47, 41, -0.7742F, 0.0F, 0.0F, 1, 4, 1, 0.0F, false));
    this.bone33 = new ModelRenderer(this);
    this.bone33.func_78793_a(-1.2653F, 1.848F, -8.5972F);
    this.bone135.func_78792_a(this.bone33);
    this.bone34 = new ModelRenderer(this);
    this.bone34.func_78793_a(1.7412F, -4.0F, 15.0F);
    this.bone33.func_78792_a(this.bone34);
    setRotationAngle(this.bone34, -1.0472F, 0.0F, 0.0F);
    this.bone34.field_78804_l.add(new ModelBox(this.bone34, 0, 8, -0.7742F, 0.0F, -1.0F, 1, 4, 2, 0.0F, false));
    this.bone35 = new ModelRenderer(this);
    this.bone35.func_78793_a(1.7412F, -4.0F, 15.0F);
    this.bone33.func_78792_a(this.bone35);
    this.bone35.field_78804_l.add(new ModelBox(this.bone35, 4, 47, -0.7742F, 0.0F, 0.0F, 1, 4, 1, 0.0F, false));
    this.bone36 = new ModelRenderer(this);
    this.bone36.func_78793_a(1.7412F, -4.0F, 15.0F);
    this.bone33.func_78792_a(this.bone36);
    setRotationAngle(this.bone36, -0.6981F, 0.0F, 0.0F);
    this.bone36.field_78804_l.add(new ModelBox(this.bone36, 10, 47, -0.7742F, 0.0F, 0.0F, 1, 3, 1, 0.0F, false));
    this.bone37 = new ModelRenderer(this);
    this.bone37.func_78793_a(1.7412F, -4.0F, 15.0F);
    this.bone33.func_78792_a(this.bone37);
    setRotationAngle(this.bone37, -0.5236F, 0.0F, 0.0F);
    this.bone37.field_78804_l.add(new ModelBox(this.bone37, 44, 32, -0.7742F, 0.0F, 0.0F, 1, 3, 1, 0.0F, false));
    this.bone50 = new ModelRenderer(this);
    this.bone50.func_78793_a(0.4759F, -4.152F, 6.4028F);
    this.bone135.func_78792_a(this.bone50);
    setRotationAngle(this.bone50, -0.4363F, 0.0F, 0.0F);
    this.bone50.field_78804_l.add(new ModelBox(this.bone50, 0, 47, -0.7742F, -1.0F, 0.0F, 1, 4, 1, 0.0F, false));
    this.bone49 = new ModelRenderer(this);
    this.bone49.func_78793_a(0.0F, 3.0F, 1.0F);
    this.bone50.func_78792_a(this.bone49);
    setRotationAngle(this.bone49, 0.1745F, 0.0F, 0.0F);
    this.bone49.field_78804_l.add(new ModelBox(this.bone49, 27, 34, -0.7742F, -4.0F, 0.0F, 1, 4, 1, 0.0F, false));
    this.bone24 = new ModelRenderer(this);
    this.bone24.func_78793_a(0.4759F, -2.152F, 4.4028F);
    this.bone135.func_78792_a(this.bone24);
    this.bone24.field_78804_l.add(new ModelBox(this.bone24, 49, 57, -0.7742F, 0.0F, 0.0F, 1, 2, 1, 0.0F, false));
    this.bone25 = new ModelRenderer(this);
    this.bone25.func_78793_a(0.4759F, -2.152F, 4.4028F);
    this.bone135.func_78792_a(this.bone25);
    setRotationAngle(this.bone25, -0.5236F, 0.0F, 0.0F);
    this.bone25.field_78804_l.add(new ModelBox(this.bone25, 28, 47, -0.7742F, 0.0F, 0.0F, 1, 3, 1, 0.0F, false));
    this.rightmouth = new ModelRenderer(this);
    this.rightmouth.func_78793_a(-8.0F, -8.0F, -3.0F);
    this.mouth.func_78792_a(this.rightmouth);
    setRotationAngle(this.rightmouth, 0.0873F, -0.3491F, 0.0F);
    this.rightmouth.field_78804_l.add(new ModelBox(this.rightmouth, 21, 31, -0.0177F, 4.0118F, -15.665F, 1, 2, 11, 0.0F, false));
    this.bone4 = new ModelRenderer(this);
    this.bone4.func_78793_a(0.2081F, 6.0118F, -4.759F);
    this.rightmouth.func_78792_a(this.bone4);
    setRotationAngle(this.bone4, 0.2618F, 0.0F, 0.0F);
    this.bone4.field_78804_l.add(new ModelBox(this.bone4, 0, 0, -0.2258F, -2.0F, 0.0941F, 1, 2, 6, 0.0F, false));
    this.bone11 = new ModelRenderer(this);
    this.bone11.func_78793_a(0.2081F, 6.0118F, 0.241F);
    this.rightmouth.func_78792_a(this.bone11);
    setRotationAngle(this.bone11, -0.4363F, 0.0F, 0.0F);
    this.bone11.field_78804_l.add(new ModelBox(this.bone11, 28, 44, -0.2258F, -1.0F, -2.9059F, 1, 1, 7, 0.0F, false));
    this.bone12 = new ModelRenderer(this);
    this.bone12.func_78793_a(0.0F, -1.0F, 4.0F);
    this.bone11.func_78792_a(this.bone12);
    setRotationAngle(this.bone12, -0.3491F, 0.0F, 0.0F);
    this.bone12.field_78804_l.add(new ModelBox(this.bone12, 10, 44, -0.2258F, 0.0F, -6.9059F, 1, 1, 7, 0.0F, false));
    this.bone13 = new ModelRenderer(this);
    this.bone13.func_78793_a(0.0F, -1.0F, 4.0F);
    this.bone11.func_78792_a(this.bone13);
    setRotationAngle(this.bone13, -0.1745F, 0.0F, 0.0F);
    this.bone13.field_78804_l.add(new ModelBox(this.bone13, 11, 34, -0.2258F, 0.0F, -6.9059F, 1, 1, 7, 0.0F, false));
    this.bone17 = new ModelRenderer(this);
    this.bone17.func_78793_a(-4.7919F, 7.0118F, 1.241F);
    this.rightmouth.func_78792_a(this.bone17);
    this.bone17.field_78804_l.add(new ModelBox(this.bone17, 17, 53, 4.7742F, -4.0F, -15.9659F, 1, 2, 1, 0.0F, false));
    this.bone18 = new ModelRenderer(this);
    this.bone18.func_78793_a(5.0F, -4.0F, -14.9659F);
    this.bone17.func_78792_a(this.bone18);
    setRotationAngle(this.bone18, 0.2618F, 0.0F, 0.0F);
    this.bone18.field_78804_l.add(new ModelBox(this.bone18, 9, 53, -0.2258F, 0.0F, -1.0F, 1, 2, 1, 0.0F, false));
    this.bone19 = new ModelRenderer(this);
    this.bone19.func_78793_a(5.0F, -4.0F, -15.9659F);
    this.bone17.func_78792_a(this.bone19);
    setRotationAngle(this.bone19, -0.2618F, 0.0F, 0.0F);
    this.bone19.field_78804_l.add(new ModelBox(this.bone19, 0, 53, -0.2258F, 0.0F, 0.0F, 1, 2, 1, 0.0F, false));
    this.bone20 = new ModelRenderer(this);
    this.bone20.func_78793_a(-4.7919F, 7.0118F, 4.241F);
    this.rightmouth.func_78792_a(this.bone20);
    this.bone20.field_78804_l.add(new ModelBox(this.bone20, 39, 52, 4.7742F, -4.0F, -15.9659F, 1, 2, 1, 0.0F, false));
    this.bone21 = new ModelRenderer(this);
    this.bone21.func_78793_a(5.0F, -4.0F, -14.9659F);
    this.bone20.func_78792_a(this.bone21);
    setRotationAngle(this.bone21, 0.2618F, 0.0F, 0.0F);
    this.bone21.field_78804_l.add(new ModelBox(this.bone21, 35, 52, -0.2258F, 0.0F, -1.0F, 1, 2, 1, 0.0F, false));
    this.bone22 = new ModelRenderer(this);
    this.bone22.func_78793_a(5.0F, -4.0F, -15.9659F);
    this.bone20.func_78792_a(this.bone22);
    setRotationAngle(this.bone22, -0.2618F, 0.0F, 0.0F);
    this.bone22.field_78804_l.add(new ModelBox(this.bone22, 50, 32, -0.2258F, 0.0F, 0.0F, 1, 2, 1, 0.0F, false));
    this.bone28 = new ModelRenderer(this);
    this.bone28.func_78793_a(-4.7919F, 7.0118F, 8.241F);
    this.rightmouth.func_78792_a(this.bone28);
    this.bone28.field_78804_l.add(new ModelBox(this.bone28, 50, 50, 4.7742F, -4.0F, -15.9659F, 1, 2, 1, 0.0F, false));
    this.bone29 = new ModelRenderer(this);
    this.bone29.func_78793_a(5.0F, -4.0F, -14.9659F);
    this.bone28.func_78792_a(this.bone29);
    setRotationAngle(this.bone29, 0.2618F, 0.0F, 0.0F);
    this.bone29.field_78804_l.add(new ModelBox(this.bone29, 48, 28, -0.2258F, 0.0F, -1.0F, 1, 2, 1, 0.0F, false));
    this.bone30 = new ModelRenderer(this);
    this.bone30.func_78793_a(5.0F, -4.0F, -15.9659F);
    this.bone28.func_78792_a(this.bone30);
    setRotationAngle(this.bone30, -0.2618F, 0.0F, 0.0F);
    this.bone30.field_78804_l.add(new ModelBox(this.bone30, 37, 18, -0.2258F, 0.0F, 0.0F, 1, 2, 1, 0.0F, false));
    this.bone31 = new ModelRenderer(this);
    this.bone31.func_78793_a(1.9493F, 6.0118F, -16.725F);
    this.rightmouth.func_78792_a(this.bone31);
    setRotationAngle(this.bone31, 0.0F, 0.9599F, 0.0F);
    this.bone31.field_78804_l.add(new ModelBox(this.bone31, 34, 31, -2.0F, -2.0F, -1.0F, 3, 2, 1, 0.0F, false));
    this.bone31.field_78804_l.add(new ModelBox(this.bone31, 37, 14, 0.0F, -4.0F, -1.0F, 1, 2, 1, 0.0F, false));
    this.bone32 = new ModelRenderer(this);
    this.bone32.func_78793_a(0.0F, -4.0F, 0.0F);
    this.bone31.func_78792_a(this.bone32);
    setRotationAngle(this.bone32, 0.0F, 0.0F, 0.3491F);
    this.bone32.field_78804_l.add(new ModelBox(this.bone32, 39, 24, 0.0F, 0.0F, -1.0F, 1, 3, 1, 0.0F, false));
    this.bone38 = new ModelRenderer(this);
    this.bone38.func_78793_a(1.9493F, 6.0118F, -16.725F);
    this.rightmouth.func_78792_a(this.bone38);
    this.bone39 = new ModelRenderer(this);
    this.bone39.func_78793_a(0.684F, 4.1638F, -6.1278F);
    this.rightmouth.func_78792_a(this.bone39);
    setRotationAngle(this.bone39, 0.1745F, 0.0F, 0.0F);
    this.bone40 = new ModelRenderer(this);
    this.bone40.func_78793_a(-0.4759F, -2.152F, 3.4028F);
    this.bone39.func_78792_a(this.bone40);
    setRotationAngle(this.bone40, -0.6981F, 0.0F, 0.0F);
    this.bone40.field_78804_l.add(new ModelBox(this.bone40, 38, 5, -0.2258F, 0.0F, 0.0F, 1, 3, 1, 0.0F, false));
    this.bone41 = new ModelRenderer(this);
    this.bone41.func_78793_a(-0.4759F, -2.152F, 3.4028F);
    this.bone39.func_78792_a(this.bone41);
    setRotationAngle(this.bone41, -1.0472F, 0.0F, 0.0F);
    this.bone41.field_78804_l.add(new ModelBox(this.bone41, 17, 23, -0.2258F, 0.0F, 0.0F, 1, 4, 1, 0.0F, false));
    this.bone42 = new ModelRenderer(this);
    this.bone42.func_78793_a(1.2653F, 1.848F, -8.5972F);
    this.bone39.func_78792_a(this.bone42);
    this.bone46 = new ModelRenderer(this);
    this.bone46.func_78793_a(-1.7412F, -4.0F, 15.0F);
    this.bone42.func_78792_a(this.bone46);
    setRotationAngle(this.bone46, -1.0472F, 0.0F, 0.0F);
    this.bone46.field_78804_l.add(new ModelBox(this.bone46, 0, 0, -0.2258F, 0.0F, -1.0F, 1, 4, 2, 0.0F, false));
    this.bone47 = new ModelRenderer(this);
    this.bone47.func_78793_a(-1.7412F, -4.0F, 15.0F);
    this.bone42.func_78792_a(this.bone47);
    this.bone47.field_78804_l.add(new ModelBox(this.bone47, 17, 6, -0.2258F, 0.0F, 0.0F, 1, 4, 1, 0.0F, false));
    this.bone48 = new ModelRenderer(this);
    this.bone48.func_78793_a(-1.7412F, -4.0F, 15.0F);
    this.bone42.func_78792_a(this.bone48);
    setRotationAngle(this.bone48, -0.6981F, 0.0F, 0.0F);
    this.bone48.field_78804_l.add(new ModelBox(this.bone48, 38, 0, -0.2258F, 0.0F, 0.0F, 1, 3, 1, 0.0F, false));
    this.bone51 = new ModelRenderer(this);
    this.bone51.func_78793_a(-1.7412F, -4.0F, 15.0F);
    this.bone42.func_78792_a(this.bone51);
    setRotationAngle(this.bone51, -0.5236F, 0.0F, 0.0F);
    this.bone51.field_78804_l.add(new ModelBox(this.bone51, 32, 5, -0.2258F, 0.0F, 0.0F, 1, 3, 1, 0.0F, false));
    this.bone52 = new ModelRenderer(this);
    this.bone52.func_78793_a(-0.4759F, -4.152F, 6.4028F);
    this.bone39.func_78792_a(this.bone52);
    setRotationAngle(this.bone52, -0.4363F, 0.0F, 0.0F);
    this.bone52.field_78804_l.add(new ModelBox(this.bone52, 17, 0, -0.2258F, -1.0F, 0.0F, 1, 4, 1, 0.0F, false));
    this.bone136 = new ModelRenderer(this);
    this.bone136.func_78793_a(0.0F, 3.0F, 1.0F);
    this.bone52.func_78792_a(this.bone136);
    setRotationAngle(this.bone136, 0.1745F, 0.0F, 0.0F);
    this.bone136.field_78804_l.add(new ModelBox(this.bone136, 17, 17, -0.2258F, -4.0F, 0.0F, 1, 4, 1, 0.0F, false));
    this.bone137 = new ModelRenderer(this);
    this.bone137.func_78793_a(-0.4759F, -2.152F, 4.4028F);
    this.bone39.func_78792_a(this.bone137);
    this.bone137.field_78804_l.add(new ModelBox(this.bone137, 5, 34, -0.2258F, 0.0F, 0.0F, 1, 2, 1, 0.0F, false));
    this.bone138 = new ModelRenderer(this);
    this.bone138.func_78793_a(-0.4759F, -2.152F, 4.4028F);
    this.bone39.func_78792_a(this.bone138);
    setRotationAngle(this.bone138, -0.5236F, 0.0F, 0.0F);
    this.bone138.field_78804_l.add(new ModelBox(this.bone138, 32, 0, -0.2258F, 0.0F, 0.0F, 1, 3, 1, 0.0F, false));
    this.head = new ModelRenderer(this);
    this.head.func_78793_a(0.0F, 0.0F, 0.0F);
    this.gasterblaster.func_78792_a(this.head);
    this.lefthead = new ModelRenderer(this);
    this.lefthead.func_78793_a(2.0F, 0.0F, -9.0F);
    this.head.func_78792_a(this.lefthead);
    setRotationAngle(this.lefthead, -0.0873F, 0.3491F, -0.0873F);
    this.bone53 = new ModelRenderer(this);
    this.bone53.func_78793_a(0.7412F, -7.8305F, 12.4125F);
    this.lefthead.func_78792_a(this.bone53);
    this.bone53.field_78804_l.add(new ModelBox(this.bone53, 17, 0, 0.2588F, -2.0F, -13.0F, 1, 1, 13, 0.0F, false));
    this.bone57 = new ModelRenderer(this);
    this.bone57.func_78793_a(1.2588F, -2.0F, -10.0F);
    this.bone53.func_78792_a(this.bone57);
    setRotationAngle(this.bone57, 0.0F, 0.0F, -0.6109F);
    this.bone57.field_78804_l.add(new ModelBox(this.bone57, 0, 17, -1.033F, -2.0F, -5.0F, 1, 2, 15, 0.0F, false));
    this.bone86 = new ModelRenderer(this);
    this.bone86.func_78793_a(-0.034F, 0.0F, -5.0025F);
    this.bone57.func_78792_a(this.bone86);
    setRotationAngle(this.bone86, 0.0F, 0.6109F, 0.0F);
    this.bone83 = new ModelRenderer(this);
    this.bone83.func_78793_a(0.0F, 0.0F, 0.0F);
    this.bone86.func_78792_a(this.bone83);
    this.bone83.field_78804_l.add(new ModelBox(this.bone83, 56, 36, -0.999F, -2.0F, -0.9975F, 1, 2, 1, 0.0F, false));
    this.bone85 = new ModelRenderer(this);
    this.bone85.func_78793_a(0.0F, 0.0F, -1.0F);
    this.bone86.func_78792_a(this.bone85);
    setRotationAngle(this.bone85, 0.0F, 0.5236F, 0.0F);
    this.bone85.field_78804_l.add(new ModelBox(this.bone85, 56, 33, -0.999F, -2.0F, -0.9975F, 1, 2, 1, 0.0F, false));
    this.bone89 = new ModelRenderer(this);
    this.bone89.func_78793_a(0.0F, 0.0F, -1.0F);
    this.bone86.func_78792_a(this.bone89);
    setRotationAngle(this.bone89, 0.9599F, 0.5236F, 0.0F);
    this.bone89.field_78804_l.add(new ModelBox(this.bone89, 56, 12, -0.999F, -1.8175F, -0.573F, 1, 1, 1, 0.0F, false));
    this.bone89.field_78804_l.add(new ModelBox(this.bone89, 54, 22, -0.999F, -1.8175F, -0.11F, 1, 1, 1, 0.0F, false));
    this.bone60 = new ModelRenderer(this);
    this.bone60.func_78793_a(0.0F, -2.0F, 0.0F);
    this.bone57.func_78792_a(this.bone60);
    setRotationAngle(this.bone60, 0.0F, 0.0F, -0.4363F);
    this.bone60.field_78804_l.add(new ModelBox(this.bone60, 70, 7, -1.033F, -1.0F, -3.0F, 1, 1, 6, 0.0F, false));
    this.bone61 = new ModelRenderer(this);
    this.bone61.func_78793_a(-2.0F, -2.0F, -3.0F);
    this.bone57.func_78792_a(this.bone61);
    setRotationAngle(this.bone61, 0.2618F, 0.0F, -0.4363F);
    this.bone61.field_78804_l.add(new ModelBox(this.bone61, 22, 69, 0.7796F, -0.1548F, 0.0F, 1, 1, 6, 0.0F, false));
    this.bone64 = new ModelRenderer(this);
    this.bone64.func_78793_a(-2.0F, -2.0F, -3.0F);
    this.bone57.func_78792_a(this.bone64);
    setRotationAngle(this.bone64, 0.3491F, 0.0F, -0.4363F);
    this.bone64.field_78804_l.add(new ModelBox(this.bone64, 46, 56, 0.7796F, -0.1548F, 0.0F, 1, 1, 7, 0.0F, false));
    this.bone68 = new ModelRenderer(this);
    this.bone68.func_78793_a(1.78F, -0.1548F, 5.9059F);
    this.bone64.func_78792_a(this.bone68);
    setRotationAngle(this.bone68, 0.0F, 0.0F, 0.3491F);
    this.bone68.field_78804_l.add(new ModelBox(this.bone68, 55, 25, -1.0004F, 0.0F, -5.9059F, 1, 1, 7, 0.0F, false));
    this.bone67 = new ModelRenderer(this);
    this.bone67.func_78793_a(-2.0F, -4.0F, 4.0F);
    this.bone57.func_78792_a(this.bone67);
    setRotationAngle(this.bone67, 0.9599F, 0.0F, -0.4363F);
    this.bone67.field_78804_l.add(new ModelBox(this.bone67, 45, 0, -0.0656F, -0.8456F, -2.1978F, 1, 1, 2, 0.0F, false));
    this.bone62 = new ModelRenderer(this);
    this.bone62.func_78793_a(-2.0F, -2.0F, -3.0F);
    this.bone57.func_78792_a(this.bone62);
    setRotationAngle(this.bone62, 0.0873F, 0.0F, -0.4363F);
    this.bone62.field_78804_l.add(new ModelBox(this.bone62, 14, 68, 0.7796F, -0.1548F, 0.0F, 1, 1, 6, 0.0F, false));
    this.bone63 = new ModelRenderer(this);
    this.bone63.func_78793_a(-2.0F, -2.0F, -3.0F);
    this.bone57.func_78792_a(this.bone63);
    setRotationAngle(this.bone63, 0.1745F, 0.0F, -0.4363F);
    this.bone63.field_78804_l.add(new ModelBox(this.bone63, 0, 68, 0.7796F, -0.1548F, 0.0F, 1, 1, 6, 0.0F, false));
    this.bone56 = new ModelRenderer(this);
    this.bone56.func_78793_a(1.0F, -7.8305F, 12.4125F);
    this.lefthead.func_78792_a(this.bone56);
    setRotationAngle(this.bone56, 0.4363F, 0.0F, 0.0F);
    this.bone54 = new ModelRenderer(this);
    this.bone54.func_78793_a(0.0F, 0.0F, 0.0F);
    this.bone56.func_78792_a(this.bone54);
    this.bone54.field_78804_l.add(new ModelBox(this.bone54, 8, 25, 0.0F, -2.0F, 0.0F, 1, 2, 2, 0.0F, false));
    this.bone58 = new ModelRenderer(this);
    this.bone58.func_78793_a(1.0F, -2.0F, 0.0F);
    this.bone54.func_78792_a(this.bone58);
    setRotationAngle(this.bone58, 0.0F, 0.0F, -0.6109F);
    this.bone58.field_78804_l.add(new ModelBox(this.bone58, 24, 17, -1.0F, -2.0F, 0.0F, 1, 2, 2, 0.0F, false));
    this.bone65 = new ModelRenderer(this);
    this.bone65.func_78793_a(1.0F, -2.0F, 0.0F);
    this.bone54.func_78792_a(this.bone65);
    setRotationAngle(this.bone65, 0.0F, 0.0F, -0.6109F);
    this.bone65.field_78804_l.add(new ModelBox(this.bone65, 17, 10, 0.0F, -3.0F, -2.0F, 0, 1, 4, 0.0F, false));
    this.bone65.field_78804_l.add(new ModelBox(this.bone65, 20, 34, -1.0F, -6.0F, 1.0F, 1, 4, 1, 0.0F, false));
    this.bone65.field_78804_l.add(new ModelBox(this.bone65, 0, 34, -1.0F, -6.8695F, -4.0F, 1, 1, 9, 0.0F, false));
    this.bone65.field_78804_l.add(new ModelBox(this.bone65, 24, 0, -1.0F, -5.8695F, 2.0F, 1, 2, 2, 0.0F, false));
    this.bone65.field_78804_l.add(new ModelBox(this.bone65, 54, 17, -1.0F, -7.8695F, -1.0F, 1, 1, 7, 0.0F, false));
    this.bone98 = new ModelRenderer(this);
    this.bone98.func_78793_a(0.0F, -2.0F, 2.0F);
    this.bone65.func_78792_a(this.bone98);
    setRotationAngle(this.bone98, 0.6981F, 0.0F, 0.0F);
    this.bone98.field_78804_l.add(new ModelBox(this.bone98, 55, 0, -1.0F, -1.0F, -0.1625F, 1, 1, 7, 0.0F, false));
    this.bone98.field_78804_l.add(new ModelBox(this.bone98, 25, 54, -1.0F, -1.0F, -0.2925F, 1, 1, 7, 0.0F, false));
    this.bone98.field_78804_l.add(new ModelBox(this.bone98, 66, 36, -1.0F, -1.9295F, 0.8375F, 1, 1, 6, 0.0F, false));
    this.bone98.field_78804_l.add(new ModelBox(this.bone98, 65, 64, -1.0F, -2.0F, 0.5875F, 1, 1, 6, 0.0F, false));
    this.bone99 = new ModelRenderer(this);
    this.bone99.func_78793_a(0.0F, 0.0F, 6.8375F);
    this.bone98.func_78792_a(this.bone99);
    setRotationAngle(this.bone99, 0.0F, -0.4808F, 0.0F);
    this.bone99.field_78804_l.add(new ModelBox(this.bone99, 44, 23, -1.0F, -1.0F, 0.0F, 1, 1, 8, 0.0F, false));
    this.bone99.field_78804_l.add(new ModelBox(this.bone99, 73, 26, -1.0F, -1.3695F, 0.0F, 1, 1, 5, 0.0F, false));
    this.bone99.field_78804_l.add(new ModelBox(this.bone99, 0, 34, -1.0F, -1.3695F, 4.75F, 1, 1, 3, 0.0F, false));
    this.bone99.field_78804_l.add(new ModelBox(this.bone99, 17, 56, -0.6F, -2.3295F, -0.4F, 1, 2, 1, -0.4F, false));
    this.bone109 = new ModelRenderer(this);
    this.bone109.func_78793_a(0.0F, -1.4995F, 5.0F);
    this.bone99.func_78792_a(this.bone109);
    setRotationAngle(this.bone109, -0.0873F, 0.0F, 0.0F);
    this.bone109.field_78804_l.add(new ModelBox(this.bone109, 53, 73, -0.6F, -0.4F, -4.6F, 1, 1, 5, -0.4F, false));
    this.bone109.field_78804_l.add(new ModelBox(this.bone109, 72, 56, -0.6F, -0.4F, -5.25F, 1, 1, 5, -0.4F, false));
    this.bone110 = new ModelRenderer(this);
    this.bone110.func_78793_a(0.0F, -1.4995F, 5.0F);
    this.bone99.func_78792_a(this.bone110);
    setRotationAngle(this.bone110, -0.0524F, 0.0F, 0.0F);
    this.bone110.field_78804_l.add(new ModelBox(this.bone110, 72, 20, -0.6F, -0.4F, -4.6F, 1, 1, 5, -0.4F, false));
    this.bone110.field_78804_l.add(new ModelBox(this.bone110, 72, 0, -0.6F, -0.4F, -5.25F, 1, 1, 5, -0.4F, false));
    this.bone111 = new ModelRenderer(this);
    this.bone111.func_78793_a(0.0F, -1.4995F, 5.0F);
    this.bone99.func_78792_a(this.bone111);
    setRotationAngle(this.bone111, -0.014F, 0.0F, 0.0F);
    this.bone111.field_78804_l.add(new ModelBox(this.bone111, 52, 65, -0.6F, -0.4F, -4.6F, 1, 1, 6, -0.4F, false));
    this.bone111.field_78804_l.add(new ModelBox(this.bone111, 68, 72, -0.6F, -0.4F, -5.25F, 1, 1, 5, -0.4F, false));
    this.bone106 = new ModelRenderer(this);
    this.bone106.func_78793_a(0.0F, -1.0F, 7.0F);
    this.bone98.func_78792_a(this.bone106);
    setRotationAngle(this.bone106, 0.0F, -0.4712F, 0.0F);
    this.bone66 = new ModelRenderer(this);
    this.bone66.func_78793_a(-0.2588F, -5.0F, 0.9875F);
    this.bone65.func_78792_a(this.bone66);
    setRotationAngle(this.bone66, 0.9599F, 0.0F, 0.0F);
    this.bone66.field_78804_l.add(new ModelBox(this.bone66, 43, 57, -0.7412F, -2.0F, 0.0125F, 1, 2, 1, 0.0F, false));
    this.bone100 = new ModelRenderer(this);
    this.bone100.func_78793_a(0.0F, -7.8695F, 0.0F);
    this.bone65.func_78792_a(this.bone100);
    setRotationAngle(this.bone100, 0.0F, 0.0F, -0.5236F);
    this.bone100.field_78804_l.add(new ModelBox(this.bone100, 74, 32, -1.0F, -1.0F, 0.0F, 1, 1, 5, 0.0F, false));
    this.bone100.field_78804_l.add(new ModelBox(this.bone100, 73, 62, -1.0F, -1.0F, 0.8375F, 1, 1, 5, 0.0F, false));
    this.bone100.field_78804_l.add(new ModelBox(this.bone100, 71, 14, -0.55F, -0.55F, 1.4475F, 1, 1, 5, -0.45F, false));
    this.bone69 = new ModelRenderer(this);
    this.bone69.func_78793_a(0.0F, 0.0F, 5.9975F);
    this.bone100.func_78792_a(this.bone69);
    setRotationAngle(this.bone69, 0.6632F, 0.0F, 0.0F);
    this.bone69.field_78804_l.add(new ModelBox(this.bone69, 47, 19, -0.55F, -0.55F, -0.45F, 1, 1, 1, -0.45F, false));
    this.bone69.field_78804_l.add(new ModelBox(this.bone69, 47, 16, -0.55F, -0.55F, -0.41F, 1, 1, 1, -0.45F, false));
    this.bone101 = new ModelRenderer(this);
    this.bone101.func_78793_a(0.0F, -7.8695F, 0.0F);
    this.bone65.func_78792_a(this.bone101);
    setRotationAngle(this.bone101, 0.2618F, 0.0F, -0.5236F);
    this.bone101.field_78804_l.add(new ModelBox(this.bone101, 65, 43, -1.0F, -1.0F, -0.0825F, 1, 1, 6, 0.0F, false));
    this.bone101.field_78804_l.add(new ModelBox(this.bone101, 19, 44, -1.0F, -1.5F, 3.9175F, 1, 1, 2, 0.0F, false));
    this.bone101.field_78804_l.add(new ModelBox(this.bone101, 47, 13, -1.0F, -1.4F, 2.9175F, 1, 1, 1, 0.0F, false));
    this.bone101.field_78804_l.add(new ModelBox(this.bone101, 45, 9, -1.0F, -1.3F, 1.9175F, 1, 1, 1, 0.0F, false));
    this.bone101.field_78804_l.add(new ModelBox(this.bone101, 45, 6, -1.0F, -1.1F, 0.9175F, 1, 1, 1, 0.0F, false));
    this.bone105 = new ModelRenderer(this);
    this.bone105.func_78793_a(0.0F, 0.0F, 5.9175F);
    this.bone101.func_78792_a(this.bone105);
    setRotationAngle(this.bone105, 0.0F, -0.1745F, 0.0F);
    this.bone105.field_78804_l.add(new ModelBox(this.bone105, 37, 49, -1.0F, -0.15F, 0.0F, 1, 1, 1, 0.0F, false));
    this.bone105.field_78804_l.add(new ModelBox(this.bone105, 34, 56, -0.6F, -0.34F, -0.4F, 1, 2, 1, -0.4F, false));
    this.bone105.field_78804_l.add(new ModelBox(this.bone105, 9, 56, -0.55F, -0.12F, -0.45F, 1, 2, 1, -0.45F, false));
    this.bone105.field_78804_l.add(new ModelBox(this.bone105, 48, 37, -0.6F, 0.43F, -0.21F, 1, 1, 1, -0.4F, false));
    this.bone108 = new ModelRenderer(this);
    this.bone108.func_78793_a(0.0F, -1.0F, 5.0F);
    this.bone105.func_78792_a(this.bone108);
    setRotationAngle(this.bone108, 0.4363F, 0.0F, 0.0F);
    this.bone108.field_78804_l.add(new ModelBox(this.bone108, 73, 43, -1.0F, -1.0F, -5.0F, 1, 1, 5, 0.0F, false));
    this.bone108.field_78804_l.add(new ModelBox(this.bone108, 45, 0, -0.6F, -0.6F, -5.73F, 1, 1, 8, -0.4F, false));
    this.bone108.field_78804_l.add(new ModelBox(this.bone108, 60, 65, -0.6F, -0.8F, -2.73F, 1, 1, 4, -0.4F, false));
    this.bone108.field_78804_l.add(new ModelBox(this.bone108, 38, 65, -0.6F, -1.0F, -2.73F, 1, 1, 4, -0.4F, false));
    this.bone112 = new ModelRenderer(this);
    this.bone112.func_78793_a(0.0F, -0.03F, -1.13F);
    this.bone108.func_78792_a(this.bone112);
    setRotationAngle(this.bone112, 0.0349F, 0.0F, 0.0F);
    this.bone112.field_78804_l.add(new ModelBox(this.bone112, 46, 72, -0.6F, -0.6F, -4.6F, 1, 1, 5, -0.4F, false));
    this.bone112.field_78804_l.add(new ModelBox(this.bone112, 31, 72, -0.6F, -0.6F, -4.75F, 1, 1, 5, -0.4F, false));
    this.bone107 = new ModelRenderer(this);
    this.bone107.func_78793_a(0.0F, -1.0F, 5.0F);
    this.bone105.func_78792_a(this.bone107);
    this.bone107.field_78804_l.add(new ModelBox(this.bone107, 34, 34, -1.0F, -0.1F, -5.0F, 1, 1, 3, 0.0F, false));
    this.bone107.field_78804_l.add(new ModelBox(this.bone107, 43, 57, -1.0F, -0.7F, -5.0F, 1, 1, 4, 0.0F, false));
    this.bone102 = new ModelRenderer(this);
    this.bone102.func_78793_a(0.0F, -7.8695F, 0.0F);
    this.bone65.func_78792_a(this.bone102);
    setRotationAngle(this.bone102, 0.2618F, 0.0F, -0.5236F);
    this.bone102.field_78804_l.add(new ModelBox(this.bone102, 37, 44, -1.0F, 0.0F, 3.9175F, 1, 1, 2, 0.0F, false));
    this.bone102.field_78804_l.add(new ModelBox(this.bone102, 46, 53, -0.6F, 0.6F, 5.3175F, 1, 1, 1, -0.4F, false));
    this.bone102.field_78804_l.add(new ModelBox(this.bone102, 49, 9, -0.6F, 0.8F, 5.3175F, 1, 1, 1, -0.4F, false));
    this.bone102.field_78804_l.add(new ModelBox(this.bone102, 49, 3, -0.6F, 0.8F, 5.1875F, 1, 1, 1, -0.4F, false));
    this.bone102.field_78804_l.add(new ModelBox(this.bone102, 49, 6, -0.6F, 0.9F, 5.3175F, 1, 1, 1, -0.4F, false));
    this.bone102.field_78804_l.add(new ModelBox(this.bone102, 49, 0, -0.6F, 0.92F, 5.2375F, 1, 1, 1, -0.4F, false));
    this.bone103 = new ModelRenderer(this);
    this.bone103.func_78793_a(0.0F, -7.8695F, 0.0F);
    this.bone65.func_78792_a(this.bone103);
    setRotationAngle(this.bone103, 0.2618F, 0.0F, -0.5236F);
    this.bone103.field_78804_l.add(new ModelBox(this.bone103, 28, 44, -1.0F, -0.3F, 3.5875F, 1, 1, 2, 0.0F, false));
    this.bone91 = new ModelRenderer(this);
    this.bone91.func_78793_a(-0.2588F, -7.0F, -4.0941F);
    this.bone65.func_78792_a(this.bone91);
    setRotationAngle(this.bone91, 0.3491F, 0.0F, 0.0F);
    this.bone91.field_78804_l.add(new ModelBox(this.bone91, 74, 50, -0.7412F, 0.1305F, 0.0941F, 1, 1, 4, 0.0F, false));
    this.bone91.field_78804_l.add(new ModelBox(this.bone91, 56, 9, -0.7412F, 0.1305F, 3.0941F, 1, 2, 1, 0.0F, false));
    this.bone91.field_78804_l.add(new ModelBox(this.bone91, 50, 53, -0.7412F, 0.3305F, 2.0941F, 1, 1, 1, 0.0F, false));
    this.bone92 = new ModelRenderer(this);
    this.bone92.func_78793_a(0.2588F, 0.1305F, 4.0F);
    this.bone91.func_78792_a(this.bone92);
    setRotationAngle(this.bone92, 0.0F, 0.0F, -0.7854F);
    this.bone92.field_78804_l.add(new ModelBox(this.bone92, 55, 56, -1.0F, -2.0F, -0.9059F, 1, 2, 1, 0.0F, false));
    this.bone97 = new ModelRenderer(this);
    this.bone97.func_78793_a(0.0F, -1.1305F, 0.094F);
    this.bone92.func_78792_a(this.bone97);
    setRotationAngle(this.bone97, 0.0873F, -0.1396F, 0.0F);
    this.bone97.field_78804_l.add(new ModelBox(this.bone97, 65, 29, -1.0F, -0.3695F, 0.0F, 1, 1, 6, 0.0F, false));
    this.bone104 = new ModelRenderer(this);
    this.bone104.func_78793_a(-2.0F, 0.0F, 11.0F);
    this.bone97.func_78792_a(this.bone104);
    setRotationAngle(this.bone104, 0.0F, -0.1745F, 0.0F);
    this.bone104.field_78804_l.add(new ModelBox(this.bone104, 47, 47, 0.1014F, -0.3695F, -5.2713F, 1, 1, 8, 0.0F, false));
    this.bone55 = new ModelRenderer(this);
    this.bone55.func_78793_a(-1.2588F, 0.0F, 2.0F);
    this.bone56.func_78792_a(this.bone55);
    setRotationAngle(this.bone55, 0.4363F, 0.0F, 0.0F);
    this.bone55.field_78804_l.add(new ModelBox(this.bone55, 0, 25, 1.2588F, -2.0F, 0.0F, 1, 2, 2, 0.0F, false));
    this.bone55.field_78804_l.add(new ModelBox(this.bone55, 64, 57, 1.2588F, -2.0F, 2.0F, 1, 1, 6, 0.0F, false));
    this.bone71 = new ModelRenderer(this);
    this.bone71.func_78793_a(2.2588F, 0.0F, 2.0F);
    this.bone55.func_78792_a(this.bone71);
    setRotationAngle(this.bone71, 0.1658F, 0.0F, 0.0F);
    this.bone71.field_78804_l.add(new ModelBox(this.bone71, 64, 22, -1.0F, -1.0F, 0.0F, 1, 1, 6, 0.0F, false));
    this.bone59 = new ModelRenderer(this);
    this.bone59.func_78793_a(2.2588F, -2.0F, 0.0F);
    this.bone55.func_78792_a(this.bone59);
    setRotationAngle(this.bone59, 0.0F, 0.0F, -0.6109F);
    this.bone59.field_78804_l.add(new ModelBox(this.bone59, 24, 6, -1.0F, -2.0F, 0.0F, 1, 2, 2, 0.0F, false));
    this.bone59.field_78804_l.add(new ModelBox(this.bone59, 18, 44, -1.0F, -1.0F, 0.0F, 1, 1, 8, 0.0F, false));
    this.bone70 = new ModelRenderer(this);
    this.bone70.func_78793_a(0.0F, -2.0F, 2.0F);
    this.bone59.func_78792_a(this.bone70);
    setRotationAngle(this.bone70, -0.1658F, 0.0F, 0.0F);
    this.bone70.field_78804_l.add(new ModelBox(this.bone70, 30, 65, -1.0F, 0.0F, 0.0F, 1, 1, 6, 0.0F, false));
    this.eye = new ModelRenderer(this);
    this.eye.func_78793_a(0.5517F, -11.0274F, 7.3127F);
    this.lefthead.func_78792_a(this.eye);
    setRotationAngle(this.eye, -0.6981F, 0.1745F, -0.5236F);
    this.eye.field_78804_l.add(new ModelBox(this.eye, 45, 24, -1.5517F, -2.0F, -1.0F, 1, 1, 2, -0.3F, false));
    this.eye.field_78804_l.add(new ModelBox(this.eye, 25, 57, -1.5517F, -3.0F, 0.0F, 1, 2, 1, -0.3F, false));
    this.eye.field_78804_l.add(new ModelBox(this.eye, 45, 3, -1.5517F, -3.0F, -1.0F, 1, 1, 2, -0.3F, false));
    this.eye.field_78804_l.add(new ModelBox(this.eye, 0, 57, -1.5517F, -3.0F, -1.0F, 1, 2, 1, -0.3F, false));
    this.righthead = new ModelRenderer(this);
    this.righthead.func_78793_a(-2.0F, 0.0F, -9.0F);
    this.head.func_78792_a(this.righthead);
    setRotationAngle(this.righthead, -0.0873F, -0.3491F, 0.0873F);
    this.bone72 = new ModelRenderer(this);
    this.bone72.func_78793_a(-0.7412F, -7.8305F, 12.4125F);
    this.righthead.func_78792_a(this.bone72);
    this.bone72.field_78804_l.add(new ModelBox(this.bone72, 17, 17, -1.2588F, -2.0F, -13.0F, 1, 1, 13, 0.0F, false));
    this.bone73 = new ModelRenderer(this);
    this.bone73.func_78793_a(-1.2588F, -2.0F, -10.0F);
    this.bone72.func_78792_a(this.bone73);
    setRotationAngle(this.bone73, 0.0F, 0.0F, 0.6109F);
    this.bone73.field_78804_l.add(new ModelBox(this.bone73, 0, 0, 0.033F, -2.0F, -5.0F, 1, 2, 15, 0.0F, false));
    this.bone74 = new ModelRenderer(this);
    this.bone74.func_78793_a(0.034F, 0.0F, -5.0025F);
    this.bone73.func_78792_a(this.bone74);
    setRotationAngle(this.bone74, 0.0F, -0.6109F, 0.0F);
    this.bone75 = new ModelRenderer(this);
    this.bone75.func_78793_a(0.0F, 0.0F, 0.0F);
    this.bone74.func_78792_a(this.bone75);
    this.bone75.field_78804_l.add(new ModelBox(this.bone75, 55, 3, -0.001F, -2.0F, -0.9975F, 1, 2, 1, 0.0F, false));
    this.bone76 = new ModelRenderer(this);
    this.bone76.func_78793_a(0.0F, 0.0F, -1.0F);
    this.bone74.func_78792_a(this.bone76);
    setRotationAngle(this.bone76, 0.0F, -0.5236F, 0.0F);
    this.bone76.field_78804_l.add(new ModelBox(this.bone76, 55, 0, -0.001F, -2.0F, -0.9975F, 1, 2, 1, 0.0F, false));
    this.bone77 = new ModelRenderer(this);
    this.bone77.func_78793_a(0.0F, 0.0F, -1.0F);
    this.bone74.func_78792_a(this.bone77);
    setRotationAngle(this.bone77, 0.9599F, -0.5236F, 0.0F);
    this.bone77.field_78804_l.add(new ModelBox(this.bone77, 44, 37, -0.001F, -1.8175F, -0.573F, 1, 1, 1, 0.0F, false));
    this.bone77.field_78804_l.add(new ModelBox(this.bone77, 41, 44, -0.001F, -1.8175F, -0.11F, 1, 1, 1, 0.0F, false));
    this.bone78 = new ModelRenderer(this);
    this.bone78.func_78793_a(0.0F, -2.0F, 0.0F);
    this.bone73.func_78792_a(this.bone78);
    setRotationAngle(this.bone78, 0.0F, 0.0F, 0.4363F);
    this.bone78.field_78804_l.add(new ModelBox(this.bone78, 64, 0, 0.033F, -1.0F, -3.0F, 1, 1, 6, 0.0F, false));
    this.bone79 = new ModelRenderer(this);
    this.bone79.func_78793_a(2.0F, -2.0F, -3.0F);
    this.bone73.func_78792_a(this.bone79);
    setRotationAngle(this.bone79, 0.2618F, 0.0F, 0.4363F);
    this.bone79.field_78804_l.add(new ModelBox(this.bone79, 44, 64, -1.7796F, -0.1548F, 0.0F, 1, 1, 6, 0.0F, false));
    this.bone80 = new ModelRenderer(this);
    this.bone80.func_78793_a(2.0F, -2.0F, -3.0F);
    this.bone73.func_78792_a(this.bone80);
    setRotationAngle(this.bone80, 0.3491F, 0.0F, 0.4363F);
    this.bone80.field_78804_l.add(new ModelBox(this.bone80, 53, 9, -1.7796F, -0.1548F, 0.0F, 1, 1, 7, 0.0F, false));
    this.bone81 = new ModelRenderer(this);
    this.bone81.func_78793_a(-1.78F, -0.1548F, 5.9059F);
    this.bone80.func_78792_a(this.bone81);
    setRotationAngle(this.bone81, 0.0F, 0.0F, -0.3491F);
    this.bone81.field_78804_l.add(new ModelBox(this.bone81, 16, 53, 4.0E-4F, 0.0F, -5.9059F, 1, 1, 7, 0.0F, false));
    this.bone82 = new ModelRenderer(this);
    this.bone82.func_78793_a(2.0F, -4.0F, 4.0F);
    this.bone73.func_78792_a(this.bone82);
    setRotationAngle(this.bone82, 0.9599F, 0.0F, 0.4363F);
    this.bone82.field_78804_l.add(new ModelBox(this.bone82, 10, 44, -0.9344F, -0.8456F, -2.1978F, 1, 1, 2, 0.0F, false));
    this.bone84 = new ModelRenderer(this);
    this.bone84.func_78793_a(2.0F, -2.0F, -3.0F);
    this.bone73.func_78792_a(this.bone84);
    setRotationAngle(this.bone84, 0.0873F, 0.0F, 0.4363F);
    this.bone84.field_78804_l.add(new ModelBox(this.bone84, 63, 15, -1.7796F, -0.1548F, 0.0F, 1, 1, 6, 0.0F, false));
    this.bone87 = new ModelRenderer(this);
    this.bone87.func_78793_a(2.0F, -2.0F, -3.0F);
    this.bone73.func_78792_a(this.bone87);
    setRotationAngle(this.bone87, 0.1745F, 0.0F, 0.4363F);
    this.bone87.field_78804_l.add(new ModelBox(this.bone87, 62, 8, -1.7796F, -0.1548F, 0.0F, 1, 1, 6, 0.0F, false));
    this.bone88 = new ModelRenderer(this);
    this.bone88.func_78793_a(-1.0F, -7.8305F, 12.4125F);
    this.righthead.func_78792_a(this.bone88);
    setRotationAngle(this.bone88, 0.4363F, 0.0F, 0.0F);
    this.bone90 = new ModelRenderer(this);
    this.bone90.func_78793_a(0.0F, 0.0F, 0.0F);
    this.bone88.func_78792_a(this.bone90);
    this.bone90.field_78804_l.add(new ModelBox(this.bone90, 24, 24, -1.0F, -2.0F, 0.0F, 1, 2, 2, 0.0F, false));
    this.bone93 = new ModelRenderer(this);
    this.bone93.func_78793_a(-1.0F, -2.0F, 0.0F);
    this.bone90.func_78792_a(this.bone93);
    setRotationAngle(this.bone93, 0.0F, 0.0F, 0.6109F);
    this.bone93.field_78804_l.add(new ModelBox(this.bone93, 8, 17, 0.0F, -2.0F, 0.0F, 1, 2, 2, 0.0F, false));
    this.bone94 = new ModelRenderer(this);
    this.bone94.func_78793_a(-1.0F, -2.0F, 0.0F);
    this.bone90.func_78792_a(this.bone94);
    setRotationAngle(this.bone94, 0.0F, 0.0F, 0.6109F);
    this.bone94.field_78804_l.add(new ModelBox(this.bone94, 17, 8, 0.0F, -3.0F, -2.0F, 0, 1, 4, 0.0F, false));
    this.bone94.field_78804_l.add(new ModelBox(this.bone94, 32, 24, 0.0F, -6.0F, 1.0F, 1, 4, 1, 0.0F, false));
    this.bone94.field_78804_l.add(new ModelBox(this.bone94, 32, 14, 0.0F, -6.8695F, -4.0F, 1, 1, 9, 0.0F, false));
    this.bone94.field_78804_l.add(new ModelBox(this.bone94, 0, 17, 0.0F, -5.8695F, 2.0F, 1, 2, 2, 0.0F, false));
    this.bone94.field_78804_l.add(new ModelBox(this.bone94, 47, 33, 0.0F, -7.8695F, -1.0F, 1, 1, 7, 0.0F, false));
    this.bone95 = new ModelRenderer(this);
    this.bone95.func_78793_a(0.0F, -2.0F, 2.0F);
    this.bone94.func_78792_a(this.bone95);
    setRotationAngle(this.bone95, 0.6981F, 0.0F, 0.0F);
    this.bone95.field_78804_l.add(new ModelBox(this.bone95, 0, 53, 0.0F, -1.0F, -0.1625F, 1, 1, 7, 0.0F, false));
    this.bone95.field_78804_l.add(new ModelBox(this.bone95, 37, 49, 0.0F, -1.0F, -0.2925F, 1, 1, 7, 0.0F, false));
    this.bone95.field_78804_l.add(new ModelBox(this.bone95, 14, 61, 0.0F, -1.9295F, 0.8375F, 1, 1, 6, 0.0F, false));
    this.bone95.field_78804_l.add(new ModelBox(this.bone95, 0, 61, 0.0F, -2.0F, 0.5875F, 1, 1, 6, 0.0F, false));
    this.bone96 = new ModelRenderer(this);
    this.bone96.func_78793_a(0.0F, 0.0F, 6.8375F);
    this.bone95.func_78792_a(this.bone96);
    setRotationAngle(this.bone96, 0.0F, 0.4808F, 0.0F);
    this.bone96.field_78804_l.add(new ModelBox(this.bone96, 0, 44, 0.0F, -1.0F, 0.0F, 1, 1, 8, 0.0F, false));
    this.bone96.field_78804_l.add(new ModelBox(this.bone96, 61, 71, 0.0F, -1.3695F, 0.0F, 1, 1, 5, 0.0F, false));
    this.bone96.field_78804_l.add(new ModelBox(this.bone96, 32, 18, 0.0F, -1.3695F, 4.75F, 1, 1, 3, 0.0F, false));
    this.bone96.field_78804_l.add(new ModelBox(this.bone96, 28, 55, -0.4F, -2.3295F, -0.4F, 1, 2, 1, -0.4F, false));
    this.bone113 = new ModelRenderer(this);
    this.bone113.func_78793_a(0.0F, -1.4995F, 5.0F);
    this.bone96.func_78792_a(this.bone113);
    setRotationAngle(this.bone113, -0.0873F, 0.0F, 0.0F);
    this.bone113.field_78804_l.add(new ModelBox(this.bone113, 39, 71, -0.4F, -0.4F, -4.6F, 1, 1, 5, -0.4F, false));
    this.bone113.field_78804_l.add(new ModelBox(this.bone113, 8, 68, -0.4F, -0.4F, -5.25F, 1, 1, 5, -0.4F, false));
    this.bone114 = new ModelRenderer(this);
    this.bone114.func_78793_a(0.0F, -1.4995F, 5.0F);
    this.bone96.func_78792_a(this.bone114);
    setRotationAngle(this.bone114, -0.0524F, 0.0F, 0.0F);
    this.bone114.field_78804_l.add(new ModelBox(this.bone114, 67, 50, -0.4F, -0.4F, -4.6F, 1, 1, 5, -0.4F, false));
    this.bone114.field_78804_l.add(new ModelBox(this.bone114, 8, 61, -0.4F, -0.4F, -5.25F, 1, 1, 5, -0.4F, false));
    this.bone115 = new ModelRenderer(this);
    this.bone115.func_78793_a(0.0F, -1.4995F, 5.0F);
    this.bone96.func_78792_a(this.bone115);
    setRotationAngle(this.bone115, -0.014F, 0.0F, 0.0F);
    this.bone115.field_78804_l.add(new ModelBox(this.bone115, 22, 62, -0.4F, -0.4F, -4.6F, 1, 1, 6, -0.4F, false));
    this.bone115.field_78804_l.add(new ModelBox(this.bone115, 47, 41, -0.4F, -0.4F, -5.25F, 1, 1, 5, -0.4F, false));
    this.bone116 = new ModelRenderer(this);
    this.bone116.func_78793_a(0.0F, -1.0F, 7.0F);
    this.bone95.func_78792_a(this.bone116);
    setRotationAngle(this.bone116, 0.0F, 0.4712F, 0.0F);
    this.bone117 = new ModelRenderer(this);
    this.bone117.func_78793_a(0.2588F, -5.0F, 0.9875F);
    this.bone94.func_78792_a(this.bone117);
    setRotationAngle(this.bone117, 0.9599F, 0.0F, 0.0F);
    this.bone117.field_78804_l.add(new ModelBox(this.bone117, 3, 55, -0.2588F, -2.0F, 0.0125F, 1, 2, 1, 0.0F, false));
    this.bone118 = new ModelRenderer(this);
    this.bone118.func_78793_a(0.0F, -7.8695F, 0.0F);
    this.bone94.func_78792_a(this.bone118);
    setRotationAngle(this.bone118, 0.0F, 0.0F, 0.5236F);
    this.bone118.field_78804_l.add(new ModelBox(this.bone118, 20, 34, 0.0F, -1.0F, 0.0F, 1, 1, 5, 0.0F, false));
    this.bone118.field_78804_l.add(new ModelBox(this.bone118, 32, 24, 0.0F, -1.0F, 0.8375F, 1, 1, 5, 0.0F, false));
    this.bone118.field_78804_l.add(new ModelBox(this.bone118, 17, 23, -0.45F, -0.55F, 1.4475F, 1, 1, 5, -0.45F, false));
    this.bone119 = new ModelRenderer(this);
    this.bone119.func_78793_a(0.0F, 0.0F, 5.9975F);
    this.bone118.func_78792_a(this.bone119);
    setRotationAngle(this.bone119, 0.6632F, 0.0F, 0.0F);
    this.bone119.field_78804_l.add(new ModelBox(this.bone119, 4, 44, -0.45F, -0.55F, -0.45F, 1, 1, 1, -0.45F, false));
    this.bone119.field_78804_l.add(new ModelBox(this.bone119, 43, 19, -0.45F, -0.55F, -0.41F, 1, 1, 1, -0.45F, false));
    this.bone120 = new ModelRenderer(this);
    this.bone120.func_78793_a(0.0F, -7.8695F, 0.0F);
    this.bone94.func_78792_a(this.bone120);
    setRotationAngle(this.bone120, 0.2618F, 0.0F, 0.5236F);
    this.bone120.field_78804_l.add(new ModelBox(this.bone120, 59, 50, 0.0F, -1.0F, -0.0825F, 1, 1, 6, 0.0F, false));
    this.bone120.field_78804_l.add(new ModelBox(this.bone120, 0, 44, 0.0F, -1.5F, 3.9175F, 1, 1, 2, 0.0F, false));
    this.bone120.field_78804_l.add(new ModelBox(this.bone120, 4, 41, 0.0F, -1.4F, 2.9175F, 1, 1, 1, 0.0F, false));
    this.bone120.field_78804_l.add(new ModelBox(this.bone120, 0, 41, 0.0F, -1.3F, 1.9175F, 1, 1, 1, 0.0F, false));
    this.bone120.field_78804_l.add(new ModelBox(this.bone120, 38, 40, 0.0F, -1.1F, 0.9175F, 1, 1, 1, 0.0F, false));
    this.bone121 = new ModelRenderer(this);
    this.bone121.func_78793_a(0.0F, 0.0F, 5.9175F);
    this.bone120.func_78792_a(this.bone121);
    setRotationAngle(this.bone121, 0.0F, 0.1745F, 0.0F);
    this.bone121.field_78804_l.add(new ModelBox(this.bone121, 34, 40, 0.0F, -0.15F, 0.0F, 1, 1, 1, 0.0F, false));
    this.bone121.field_78804_l.add(new ModelBox(this.bone121, 54, 42, -0.4F, -0.34F, -0.4F, 1, 2, 1, -0.4F, false));
    this.bone121.field_78804_l.add(new ModelBox(this.bone121, 54, 28, -0.45F, -0.12F, -0.45F, 1, 2, 1, -0.45F, false));
    this.bone121.field_78804_l.add(new ModelBox(this.bone121, 27, 40, -0.4F, 0.43F, -0.21F, 1, 1, 1, -0.4F, false));
    this.bone122 = new ModelRenderer(this);
    this.bone122.func_78793_a(0.0F, -1.0F, 5.0F);
    this.bone121.func_78792_a(this.bone122);
    setRotationAngle(this.bone122, 0.4363F, 0.0F, 0.0F);
    this.bone122.field_78804_l.add(new ModelBox(this.bone122, 17, 6, 0.0F, -1.0F, -5.0F, 1, 1, 5, 0.0F, false));
    this.bone122.field_78804_l.add(new ModelBox(this.bone122, 43, 13, -0.4F, -0.6F, -5.73F, 1, 1, 8, -0.4F, false));
    this.bone122.field_78804_l.add(new ModelBox(this.bone122, 44, 32, -0.4F, -0.8F, -2.73F, 1, 1, 4, -0.4F, false));
    this.bone122.field_78804_l.add(new ModelBox(this.bone122, 39, 24, -0.4F, -1.0F, -2.73F, 1, 1, 4, -0.4F, false));
    this.bone123 = new ModelRenderer(this);
    this.bone123.func_78793_a(0.0F, -0.03F, -1.13F);
    this.bone122.func_78792_a(this.bone123);
    setRotationAngle(this.bone123, 0.0349F, 0.0F, 0.0F);
    this.bone123.field_78804_l.add(new ModelBox(this.bone123, 17, 0, -0.4F, -0.6F, -4.6F, 1, 1, 5, -0.4F, false));
    this.bone123.field_78804_l.add(new ModelBox(this.bone123, 17, 17, -0.4F, -0.6F, -4.75F, 1, 1, 5, -0.4F, false));
    this.bone124 = new ModelRenderer(this);
    this.bone124.func_78793_a(0.0F, -1.0F, 5.0F);
    this.bone121.func_78792_a(this.bone124);
    this.bone124.field_78804_l.add(new ModelBox(this.bone124, 32, 14, 0.0F, -0.1F, -5.0F, 1, 1, 3, 0.0F, false));
    this.bone124.field_78804_l.add(new ModelBox(this.bone124, 32, 5, 0.0F, -0.7F, -5.0F, 1, 1, 4, 0.0F, false));
    this.bone125 = new ModelRenderer(this);
    this.bone125.func_78793_a(0.0F, -7.8695F, 0.0F);
    this.bone94.func_78792_a(this.bone125);
    setRotationAngle(this.bone125, 0.2618F, 0.0F, 0.5236F);
    this.bone125.field_78804_l.add(new ModelBox(this.bone125, 43, 16, 0.0F, 0.0F, 3.9175F, 1, 1, 2, 0.0F, false));
    this.bone125.field_78804_l.add(new ModelBox(this.bone125, 8, 29, -0.4F, 0.6F, 5.3175F, 1, 1, 1, -0.4F, false));
    this.bone125.field_78804_l.add(new ModelBox(this.bone125, 0, 29, -0.4F, 0.8F, 5.3175F, 1, 1, 1, -0.4F, false));
    this.bone125.field_78804_l.add(new ModelBox(this.bone125, 8, 21, -0.4F, 0.8F, 5.1875F, 1, 1, 1, -0.4F, false));
    this.bone125.field_78804_l.add(new ModelBox(this.bone125, 0, 21, -0.4F, 0.9F, 5.3175F, 1, 1, 1, -0.4F, false));
    this.bone125.field_78804_l.add(new ModelBox(this.bone125, 8, 12, -0.4F, 0.92F, 5.2375F, 1, 1, 1, -0.4F, false));
    this.bone126 = new ModelRenderer(this);
    this.bone126.func_78793_a(0.0F, -7.8695F, 0.0F);
    this.bone94.func_78792_a(this.bone126);
    setRotationAngle(this.bone126, 0.2618F, 0.0F, 0.5236F);
    this.bone126.field_78804_l.add(new ModelBox(this.bone126, 43, 13, 0.0F, -0.3F, 3.5875F, 1, 1, 2, 0.0F, false));
    this.bone127 = new ModelRenderer(this);
    this.bone127.func_78793_a(0.2588F, -7.0F, -4.0941F);
    this.bone94.func_78792_a(this.bone127);
    setRotationAngle(this.bone127, 0.3491F, 0.0F, 0.0F);
    this.bone127.field_78804_l.add(new ModelBox(this.bone127, 32, 0, -0.2588F, 0.1305F, 0.0941F, 1, 1, 4, 0.0F, false));
    this.bone127.field_78804_l.add(new ModelBox(this.bone127, 53, 17, -0.2588F, 0.1305F, 3.0941F, 1, 2, 1, 0.0F, false));
    this.bone127.field_78804_l.add(new ModelBox(this.bone127, 8, 4, -0.2588F, 0.3305F, 2.0941F, 1, 1, 1, 0.0F, false));
    this.bone128 = new ModelRenderer(this);
    this.bone128.func_78793_a(-0.2588F, 0.1305F, 4.0F);
    this.bone127.func_78792_a(this.bone128);
    setRotationAngle(this.bone128, 0.0F, 0.0F, 0.7854F);
    this.bone128.field_78804_l.add(new ModelBox(this.bone128, 54, 25, 0.0F, -2.0F, -0.9059F, 1, 2, 1, 0.0F, false));
    this.bone129 = new ModelRenderer(this);
    this.bone129.func_78793_a(0.0F, -1.1305F, 0.094F);
    this.bone128.func_78792_a(this.bone129);
    setRotationAngle(this.bone129, 0.0873F, 0.1396F, 0.0F);
    this.bone129.field_78804_l.add(new ModelBox(this.bone129, 57, 42, 0.0F, -0.3695F, 0.0F, 1, 1, 6, 0.0F, false));
    this.bone130 = new ModelRenderer(this);
    this.bone130.func_78793_a(2.0F, 0.0F, 11.0F);
    this.bone129.func_78792_a(this.bone130);
    setRotationAngle(this.bone130, 0.0F, 0.1745F, 0.0F);
    this.bone130.field_78804_l.add(new ModelBox(this.bone130, 37, 40, -1.1014F, -0.3695F, -5.2713F, 1, 1, 8, 0.0F, false));
    this.bone131 = new ModelRenderer(this);
    this.bone131.func_78793_a(0.2588F, 0.0F, 2.0F);
    this.bone88.func_78792_a(this.bone131);
    setRotationAngle(this.bone131, 0.4363F, 0.0F, 0.0F);
    this.bone131.field_78804_l.add(new ModelBox(this.bone131, 8, 0, -1.2588F, -2.0F, 0.0F, 1, 2, 2, 0.0F, false));
    this.bone131.field_78804_l.add(new ModelBox(this.bone131, 0, 25, -1.2588F, -2.0F, 2.0F, 1, 1, 6, 0.0F, false));
    this.bone132 = new ModelRenderer(this);
    this.bone132.func_78793_a(-1.2588F, 0.0F, 2.0F);
    this.bone131.func_78792_a(this.bone132);
    setRotationAngle(this.bone132, 0.1658F, 0.0F, 0.0F);
    this.bone132.field_78804_l.add(new ModelBox(this.bone132, 9, 53, 0.0F, -1.0F, 0.0F, 1, 1, 6, 0.0F, false));
    this.bone133 = new ModelRenderer(this);
    this.bone133.func_78793_a(-1.2588F, -2.0F, 0.0F);
    this.bone131.func_78792_a(this.bone133);
    setRotationAngle(this.bone133, 0.0F, 0.0F, 0.6109F);
    this.bone133.field_78804_l.add(new ModelBox(this.bone133, 8, 8, 0.0F, -2.0F, 0.0F, 1, 2, 2, 0.0F, false));
    this.bone133.field_78804_l.add(new ModelBox(this.bone133, 34, 31, 0.0F, -1.0F, 0.0F, 1, 1, 8, 0.0F, false));
    this.bone134 = new ModelRenderer(this);
    this.bone134.func_78793_a(0.0F, -2.0F, 2.0F);
    this.bone133.func_78792_a(this.bone134);
    setRotationAngle(this.bone134, -0.1658F, 0.0F, 0.0F);
    this.bone134.field_78804_l.add(new ModelBox(this.bone134, 0, 8, 0.0F, 0.0F, 0.0F, 1, 1, 6, 0.0F, false));
    this.eye2 = new ModelRenderer(this);
    this.eye2.func_78793_a(-0.5517F, -11.0274F, 7.3127F);
    this.righthead.func_78792_a(this.eye2);
    setRotationAngle(this.eye2, -0.6981F, -0.1745F, 0.5236F);
    this.eye2.field_78804_l.add(new ModelBox(this.eye2, 11, 37, 0.5517F, -2.0F, -1.0F, 1, 1, 2, -0.3F, false));
    this.eye2.field_78804_l.add(new ModelBox(this.eye2, 53, 13, 0.5517F, -3.0F, 0.0F, 1, 2, 1, -0.3F, false));
    this.eye2.field_78804_l.add(new ModelBox(this.eye2, 11, 34, 0.5517F, -3.0F, -1.0F, 1, 1, 2, -0.3F, false));
    this.eye2.field_78804_l.add(new ModelBox(this.eye2, 25, 53, 0.5517F, -3.0F, -1.0F, 1, 2, 1, -0.3F, false));
  }
  
  public void func_78088_a(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    func_78087_a(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
    GlStateManager.func_179094_E();
    if (this.field_78091_s) {
      GlStateManager.func_179152_a(0.5F, 0.5F, 1.0F);
      this.gasterblaster.func_78785_a(scale);
    } else {
      this.gasterblaster.func_78785_a(scale);
    } 
    GlStateManager.func_179121_F();
  }
  
  public void func_78087_a(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
    this.gasterblaster.field_78796_g = netHeadYaw * 0.017453292F;
    this.gasterblaster.field_78795_f = headPitch * 0.017453292F;
    EntityGasterBlaster blaster = (EntityGasterBlaster)entityIn;
    if (blaster.fireLength > 10) {
      float open = MathHelper.func_76126_a((blaster.fireLength - 10) * 0.075F) * 0.6982F;
      if (open > 0.6982F || blaster.fireLength > 20)
        open = 0.6982F; 
      this.leftmouth.field_78796_g = -open;
      this.rightmouth.field_78796_g = open;
      this.eye.field_78807_k = true;
      this.eye2.field_78807_k = true;
    } else {
      this.leftmouth.field_78796_g = 0.3491F;
      this.rightmouth.field_78796_g = -0.3491F;
      this.eye.field_78807_k = false;
      this.eye2.field_78807_k = false;
    } 
  }
  
  public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
    modelRenderer.field_78795_f = x;
    modelRenderer.field_78796_g = y;
    modelRenderer.field_78808_h = z;
  }
}
