package ch.szclsb.orbis.driver;

import java.lang.foreign.MemoryAddress;
import java.lang.foreign.MemorySegment;

public interface OpenGL {
    public static final int GL_FALSE = 0;
    public static final int GL_TRUE = 1;

    /** AccumOp */
    public static final int GL_ACCUM  = 0x100;
    public static final int GL_LOAD   = 0x101;
    public static final int GL_RETURN = 0x102;
    public static final int GL_MULT   = 0x103;
    public static final int GL_ADD    = 0x104;

    /** AlphaFunction */
    public static final int GL_NEVER    = 0x200;
    public static final int GL_LESS     = 0x201;
    public static final int GL_EQUAL    = 0x202;
    public static final int GL_LEQUAL   = 0x203;
    public static final int GL_GREATER  = 0x204;
    public static final int GL_NOTEQUAL = 0x205;
    public static final int GL_GEQUAL   = 0x206;
    public static final int GL_ALWAYS   = 0x207;

    /** AttribMask */
    public static final int GL_CURRENT_BIT         = 0x1;
    public static final int GL_POINT_BIT           = 0x2;
    public static final int GL_LINE_BIT            = 0x4;
    public static final int GL_POLYGON_BIT         = 0x8;
    public static final int GL_POLYGON_STIPPLE_BIT = 0x10;
    public static final int GL_PIXEL_MODE_BIT      = 0x20;
    public static final int GL_LIGHTING_BIT        = 0x40;
    public static final int GL_FOG_BIT             = 0x80;
    public static final int GL_DEPTH_BUFFER_BIT    = 0x100;
    public static final int GL_ACCUM_BUFFER_BIT    = 0x200;
    public static final int GL_STENCIL_BUFFER_BIT  = 0x400;
    public static final int GL_VIEWPORT_BIT        = 0x800;
    public static final int GL_TRANSFORM_BIT       = 0x1000;
    public static final int GL_ENABLE_BIT          = 0x2000;
    public static final int GL_COLOR_BUFFER_BIT    = 0x4000;
    public static final int GL_HINT_BIT            = 0x8000;
    public static final int GL_EVAL_BIT            = 0x10000;
    public static final int GL_LIST_BIT            = 0x20000;
    public static final int GL_TEXTURE_BIT         = 0x40000;
    public static final int GL_SCISSOR_BIT         = 0x80000;
    public static final int GL_ALL_ATTRIB_BITS     = 0xFFFFF;

    /** BeginMode */
    public static final int GL_POINTS         = 0x0;
    public static final int GL_LINES          = 0x1;
    public static final int GL_LINE_LOOP      = 0x2;
    public static final int GL_LINE_STRIP     = 0x3;
    public static final int GL_TRIANGLES      = 0x4;
    public static final int GL_TRIANGLE_STRIP = 0x5;
    public static final int GL_TRIANGLE_FAN   = 0x6;
    public static final int GL_QUADS          = 0x7;
    public static final int GL_QUAD_STRIP     = 0x8;
    public static final int GL_POLYGON        = 0x9;

    /** BlendingFactorDest */
    public static final int GL_ZERO                = 0;
    public static final int GL_ONE                 = 1;
    public static final int GL_SRC_COLOR           = 0x300;
    public static final int GL_ONE_MINUS_SRC_COLOR = 0x301;
    public static final int GL_SRC_ALPHA           = 0x302;
    public static final int GL_ONE_MINUS_SRC_ALPHA = 0x303;
    public static final int GL_DST_ALPHA           = 0x304;
    public static final int GL_ONE_MINUS_DST_ALPHA = 0x305;

    /** BlendingFactorSrc */
    public static final int GL_DST_COLOR           = 0x306;
    public static final int GL_ONE_MINUS_DST_COLOR = 0x307;
    public static final int GL_SRC_ALPHA_SATURATE  = 0x308;

    /** ErrorCode */
    public static final int GL_NO_ERROR          = 0;
    public static final int GL_INVALID_ENUM      = 0x500;
    public static final int GL_INVALID_VALUE     = 0x501;
    public static final int GL_INVALID_OPERATION = 0x502;
    public static final int GL_STACK_OVERFLOW    = 0x503;
    public static final int GL_STACK_UNDERFLOW   = 0x504;
    public static final int GL_OUT_OF_MEMORY     = 0x505;

    /** FeedBackMode */
    public static final int GL_2D               = 0x600;
    public static final int GL_3D               = 0x601;
    public static final int GL_3D_COLOR         = 0x602;
    public static final int GL_3D_COLOR_TEXTURE = 0x603;
    public static final int GL_4D_COLOR_TEXTURE = 0x604;

    /** FeedBackToken */
    public static final int GL_PASS_THROUGH_TOKEN = 0x700;
    public static final int GL_POINT_TOKEN        = 0x701;
    public static final int GL_LINE_TOKEN         = 0x702;
    public static final int GL_POLYGON_TOKEN      = 0x703;
    public static final int GL_BITMAP_TOKEN       = 0x704;
    public static final int GL_DRAW_PIXEL_TOKEN   = 0x705;
    public static final int GL_COPY_PIXEL_TOKEN   = 0x706;
    public static final int GL_LINE_RESET_TOKEN   = 0x707;

    /** FogMode */
    public static final int GL_EXP  = 0x800;
    public static final int GL_EXP2 = 0x801;

    /** FrontFaceDirection */
    public static final int GL_CW  = 0x900;
    public static final int GL_CCW = 0x901;

    /** GetMapTarget */
    public static final int GL_COEFF  = 0xA00;
    public static final int GL_ORDER  = 0xA01;
    public static final int GL_DOMAIN = 0xA02;

    /** GetTarget */
    public static final int GL_CURRENT_COLOR                 = 0xB00;
    public static final int GL_CURRENT_INDEX                 = 0xB01;
    public static final int GL_CURRENT_NORMAL                = 0xB02;
    public static final int GL_CURRENT_TEXTURE_COORDS        = 0xB03;
    public static final int GL_CURRENT_RASTER_COLOR          = 0xB04;
    public static final int GL_CURRENT_RASTER_INDEX          = 0xB05;
    public static final int GL_CURRENT_RASTER_TEXTURE_COORDS = 0xB06;
    public static final int GL_CURRENT_RASTER_POSITION       = 0xB07;
    public static final int GL_CURRENT_RASTER_POSITION_VALID = 0xB08;
    public static final int GL_CURRENT_RASTER_DISTANCE       = 0xB09;
    public static final int GL_POINT_SMOOTH                  = 0xB10;
    public static final int GL_POINT_SIZE                    = 0xB11;
    public static final int GL_POINT_SIZE_RANGE              = 0xB12;
    public static final int GL_POINT_SIZE_GRANULARITY        = 0xB13;
    public static final int GL_LINE_SMOOTH                   = 0xB20;
    public static final int GL_LINE_WIDTH                    = 0xB21;
    public static final int GL_LINE_WIDTH_RANGE              = 0xB22;
    public static final int GL_LINE_WIDTH_GRANULARITY        = 0xB23;
    public static final int GL_LINE_STIPPLE                  = 0xB24;
    public static final int GL_LINE_STIPPLE_PATTERN          = 0xB25;
    public static final int GL_LINE_STIPPLE_REPEAT           = 0xB26;
    public static final int GL_LIST_MODE                     = 0xB30;
    public static final int GL_MAX_LIST_NESTING              = 0xB31;
    public static final int GL_LIST_BASE                     = 0xB32;
    public static final int GL_LIST_INDEX                    = 0xB33;
    public static final int GL_POLYGON_MODE                  = 0xB40;
    public static final int GL_POLYGON_SMOOTH                = 0xB41;
    public static final int GL_POLYGON_STIPPLE               = 0xB42;
    public static final int GL_EDGE_FLAG                     = 0xB43;
    public static final int GL_CULL_FACE                     = 0xB44;
    public static final int GL_CULL_FACE_MODE                = 0xB45;
    public static final int GL_FRONT_FACE                    = 0xB46;
    public static final int GL_LIGHTING                      = 0xB50;
    public static final int GL_LIGHT_MODEL_LOCAL_VIEWER      = 0xB51;
    public static final int GL_LIGHT_MODEL_TWO_SIDE          = 0xB52;
    public static final int GL_LIGHT_MODEL_AMBIENT           = 0xB53;
    public static final int GL_SHADE_MODEL                   = 0xB54;
    public static final int GL_COLOR_MATERIAL_FACE           = 0xB55;
    public static final int GL_COLOR_MATERIAL_PARAMETER      = 0xB56;
    public static final int GL_COLOR_MATERIAL                = 0xB57;
    public static final int GL_FOG                           = 0xB60;
    public static final int GL_FOG_INDEX                     = 0xB61;
    public static final int GL_FOG_DENSITY                   = 0xB62;
    public static final int GL_FOG_START                     = 0xB63;
    public static final int GL_FOG_END                       = 0xB64;
    public static final int GL_FOG_MODE                      = 0xB65;
    public static final int GL_FOG_COLOR                     = 0xB66;
    public static final int GL_DEPTH_RANGE                   = 0xB70;
    public static final int GL_DEPTH_TEST                    = 0xB71;
    public static final int GL_DEPTH_WRITEMASK               = 0xB72;
    public static final int GL_DEPTH_CLEAR_VALUE             = 0xB73;
    public static final int GL_DEPTH_FUNC                    = 0xB74;
    public static final int GL_ACCUM_CLEAR_VALUE             = 0xB80;
    public static final int GL_STENCIL_TEST                  = 0xB90;
    public static final int GL_STENCIL_CLEAR_VALUE           = 0xB91;
    public static final int GL_STENCIL_FUNC                  = 0xB92;
    public static final int GL_STENCIL_VALUE_MASK            = 0xB93;
    public static final int GL_STENCIL_FAIL                  = 0xB94;
    public static final int GL_STENCIL_PASS_DEPTH_FAIL       = 0xB95;
    public static final int GL_STENCIL_PASS_DEPTH_PASS       = 0xB96;
    public static final int GL_STENCIL_REF                   = 0xB97;
    public static final int GL_STENCIL_WRITEMASK             = 0xB98;
    public static final int GL_MATRIX_MODE                   = 0xBA0;
    public static final int GL_NORMALIZE                     = 0xBA1;
    public static final int GL_VIEWPORT                      = 0xBA2;
    public static final int GL_MODELVIEW_STACK_DEPTH         = 0xBA3;
    public static final int GL_PROJECTION_STACK_DEPTH        = 0xBA4;
    public static final int GL_TEXTURE_STACK_DEPTH           = 0xBA5;
    public static final int GL_MODELVIEW_MATRIX              = 0xBA6;
    public static final int GL_PROJECTION_MATRIX             = 0xBA7;
    public static final int GL_TEXTURE_MATRIX                = 0xBA8;
    public static final int GL_ATTRIB_STACK_DEPTH            = 0xBB0;
    public static final int GL_CLIENT_ATTRIB_STACK_DEPTH     = 0xBB1;
    public static final int GL_ALPHA_TEST                    = 0xBC0;
    public static final int GL_ALPHA_TEST_FUNC               = 0xBC1;
    public static final int GL_ALPHA_TEST_REF                = 0xBC2;
    public static final int GL_DITHER                        = 0xBD0;
    public static final int GL_BLEND_DST                     = 0xBE0;
    public static final int GL_BLEND_SRC                     = 0xBE1;
    public static final int GL_BLEND                         = 0xBE2;
    public static final int GL_LOGIC_OP_MODE                 = 0xBF0;
    public static final int GL_INDEX_LOGIC_OP                = 0xBF1;
    public static final int GL_LOGIC_OP                      = 0xBF1;
    public static final int GL_COLOR_LOGIC_OP                = 0xBF2;
    public static final int GL_AUX_BUFFERS                   = 0xC00;
    public static final int GL_DRAW_BUFFER                   = 0xC01;
    public static final int GL_READ_BUFFER                   = 0xC02;
    public static final int GL_SCISSOR_BOX                   = 0xC10;
    public static final int GL_SCISSOR_TEST                  = 0xC11;
    public static final int GL_INDEX_CLEAR_VALUE             = 0xC20;
    public static final int GL_INDEX_WRITEMASK               = 0xC21;
    public static final int GL_COLOR_CLEAR_VALUE             = 0xC22;
    public static final int GL_COLOR_WRITEMASK               = 0xC23;
    public static final int GL_INDEX_MODE                    = 0xC30;
    public static final int GL_RGBA_MODE                     = 0xC31;
    public static final int GL_DOUBLEBUFFER                  = 0xC32;
    public static final int GL_STEREO                        = 0xC33;
    public static final int GL_RENDER_MODE                   = 0xC40;
    public static final int GL_PERSPECTIVE_CORRECTION_HINT   = 0xC50;
    public static final int GL_POINT_SMOOTH_HINT             = 0xC51;
    public static final int GL_LINE_SMOOTH_HINT              = 0xC52;
    public static final int GL_POLYGON_SMOOTH_HINT           = 0xC53;
    public static final int GL_FOG_HINT                      = 0xC54;
    public static final int GL_TEXTURE_GEN_S                 = 0xC60;
    public static final int GL_TEXTURE_GEN_T                 = 0xC61;
    public static final int GL_TEXTURE_GEN_R                 = 0xC62;
    public static final int GL_TEXTURE_GEN_Q                 = 0xC63;
    public static final int GL_PIXEL_MAP_I_TO_I              = 0xC70;
    public static final int GL_PIXEL_MAP_S_TO_S              = 0xC71;
    public static final int GL_PIXEL_MAP_I_TO_R              = 0xC72;
    public static final int GL_PIXEL_MAP_I_TO_G              = 0xC73;
    public static final int GL_PIXEL_MAP_I_TO_B              = 0xC74;
    public static final int GL_PIXEL_MAP_I_TO_A              = 0xC75;
    public static final int GL_PIXEL_MAP_R_TO_R              = 0xC76;
    public static final int GL_PIXEL_MAP_G_TO_G              = 0xC77;
    public static final int GL_PIXEL_MAP_B_TO_B              = 0xC78;
    public static final int GL_PIXEL_MAP_A_TO_A              = 0xC79;
    public static final int GL_PIXEL_MAP_I_TO_I_SIZE         = 0xCB0;
    public static final int GL_PIXEL_MAP_S_TO_S_SIZE         = 0xCB1;
    public static final int GL_PIXEL_MAP_I_TO_R_SIZE         = 0xCB2;
    public static final int GL_PIXEL_MAP_I_TO_G_SIZE         = 0xCB3;
    public static final int GL_PIXEL_MAP_I_TO_B_SIZE         = 0xCB4;
    public static final int GL_PIXEL_MAP_I_TO_A_SIZE         = 0xCB5;
    public static final int GL_PIXEL_MAP_R_TO_R_SIZE         = 0xCB6;
    public static final int GL_PIXEL_MAP_G_TO_G_SIZE         = 0xCB7;
    public static final int GL_PIXEL_MAP_B_TO_B_SIZE         = 0xCB8;
    public static final int GL_PIXEL_MAP_A_TO_A_SIZE         = 0xCB9;
    public static final int GL_UNPACK_SWAP_BYTES             = 0xCF0;
    public static final int GL_UNPACK_LSB_FIRST              = 0xCF1;
    public static final int GL_UNPACK_ROW_LENGTH             = 0xCF2;
    public static final int GL_UNPACK_SKIP_ROWS              = 0xCF3;
    public static final int GL_UNPACK_SKIP_PIXELS            = 0xCF4;
    public static final int GL_UNPACK_ALIGNMENT              = 0xCF5;
    public static final int GL_PACK_SWAP_BYTES               = 0xD00;
    public static final int GL_PACK_LSB_FIRST                = 0xD01;
    public static final int GL_PACK_ROW_LENGTH               = 0xD02;
    public static final int GL_PACK_SKIP_ROWS                = 0xD03;
    public static final int GL_PACK_SKIP_PIXELS              = 0xD04;
    public static final int GL_PACK_ALIGNMENT                = 0xD05;
    public static final int GL_MAP_COLOR                     = 0xD10;
    public static final int GL_MAP_STENCIL                   = 0xD11;
    public static final int GL_INDEX_SHIFT                   = 0xD12;
    public static final int GL_INDEX_OFFSET                  = 0xD13;
    public static final int GL_RED_SCALE                     = 0xD14;
    public static final int GL_RED_BIAS                      = 0xD15;
    public static final int GL_ZOOM_X                        = 0xD16;
    public static final int GL_ZOOM_Y                        = 0xD17;
    public static final int GL_GREEN_SCALE                   = 0xD18;
    public static final int GL_GREEN_BIAS                    = 0xD19;
    public static final int GL_BLUE_SCALE                    = 0xD1A;
    public static final int GL_BLUE_BIAS                     = 0xD1B;
    public static final int GL_ALPHA_SCALE                   = 0xD1C;
    public static final int GL_ALPHA_BIAS                    = 0xD1D;
    public static final int GL_DEPTH_SCALE                   = 0xD1E;
    public static final int GL_DEPTH_BIAS                    = 0xD1F;
    public static final int GL_MAX_EVAL_ORDER                = 0xD30;
    public static final int GL_MAX_LIGHTS                    = 0xD31;
    public static final int GL_MAX_CLIP_PLANES               = 0xD32;
    public static final int GL_MAX_TEXTURE_SIZE              = 0xD33;
    public static final int GL_MAX_PIXEL_MAP_TABLE           = 0xD34;
    public static final int GL_MAX_ATTRIB_STACK_DEPTH        = 0xD35;
    public static final int GL_MAX_MODELVIEW_STACK_DEPTH     = 0xD36;
    public static final int GL_MAX_NAME_STACK_DEPTH          = 0xD37;
    public static final int GL_MAX_PROJECTION_STACK_DEPTH    = 0xD38;
    public static final int GL_MAX_TEXTURE_STACK_DEPTH       = 0xD39;
    public static final int GL_MAX_VIEWPORT_DIMS             = 0xD3A;
    public static final int GL_MAX_CLIENT_ATTRIB_STACK_DEPTH = 0xD3B;
    public static final int GL_SUBPIXEL_BITS                 = 0xD50;
    public static final int GL_INDEX_BITS                    = 0xD51;
    public static final int GL_RED_BITS                      = 0xD52;
    public static final int GL_GREEN_BITS                    = 0xD53;
    public static final int GL_BLUE_BITS                     = 0xD54;
    public static final int GL_ALPHA_BITS                    = 0xD55;
    public static final int GL_DEPTH_BITS                    = 0xD56;
    public static final int GL_STENCIL_BITS                  = 0xD57;
    public static final int GL_ACCUM_RED_BITS                = 0xD58;
    public static final int GL_ACCUM_GREEN_BITS              = 0xD59;
    public static final int GL_ACCUM_BLUE_BITS               = 0xD5A;
    public static final int GL_ACCUM_ALPHA_BITS              = 0xD5B;
    public static final int GL_NAME_STACK_DEPTH              = 0xD70;
    public static final int GL_AUTO_NORMAL                   = 0xD80;
    public static final int GL_MAP1_COLOR_4                  = 0xD90;
    public static final int GL_MAP1_INDEX                    = 0xD91;
    public static final int GL_MAP1_NORMAL                   = 0xD92;
    public static final int GL_MAP1_TEXTURE_COORD_1          = 0xD93;
    public static final int GL_MAP1_TEXTURE_COORD_2          = 0xD94;
    public static final int GL_MAP1_TEXTURE_COORD_3          = 0xD95;
    public static final int GL_MAP1_TEXTURE_COORD_4          = 0xD96;
    public static final int GL_MAP1_VERTEX_3                 = 0xD97;
    public static final int GL_MAP1_VERTEX_4                 = 0xD98;
    public static final int GL_MAP2_COLOR_4                  = 0xDB0;
    public static final int GL_MAP2_INDEX                    = 0xDB1;
    public static final int GL_MAP2_NORMAL                   = 0xDB2;
    public static final int GL_MAP2_TEXTURE_COORD_1          = 0xDB3;
    public static final int GL_MAP2_TEXTURE_COORD_2          = 0xDB4;
    public static final int GL_MAP2_TEXTURE_COORD_3          = 0xDB5;
    public static final int GL_MAP2_TEXTURE_COORD_4          = 0xDB6;
    public static final int GL_MAP2_VERTEX_3                 = 0xDB7;
    public static final int GL_MAP2_VERTEX_4                 = 0xDB8;
    public static final int GL_MAP1_GRID_DOMAIN              = 0xDD0;
    public static final int GL_MAP1_GRID_SEGMENTS            = 0xDD1;
    public static final int GL_MAP2_GRID_DOMAIN              = 0xDD2;
    public static final int GL_MAP2_GRID_SEGMENTS            = 0xDD3;
    public static final int GL_TEXTURE_1D                    = 0xDE0;
    public static final int GL_TEXTURE_2D                    = 0xDE1;
    public static final int GL_FEEDBACK_BUFFER_POINTER       = 0xDF0;
    public static final int GL_FEEDBACK_BUFFER_SIZE          = 0xDF1;
    public static final int GL_FEEDBACK_BUFFER_TYPE          = 0xDF2;
    public static final int GL_SELECTION_BUFFER_POINTER      = 0xDF3;
    public static final int GL_SELECTION_BUFFER_SIZE         = 0xDF4;

    /** Types */
    public static final int GL_BYTE           = 0x1400;
    public static final int GL_UNSIGNED_BYTE  = 0x1401;
    public static final int GL_SHORT          = 0x1402;
    public static final int GL_UNSIGNED_SHORT = 0x1403;
    public static final int GL_INT            = 0x1404;
    public static final int GL_UNSIGNED_INT   = 0x1405;
    public static final int GL_FLOAT          = 0x1406;
    public static final int GL_2_BYTES        = 0x1407;
    public static final int GL_3_BYTES        = 0x1408;
    public static final int GL_4_BYTES        = 0x1409;
    public static final int GL_DOUBLE         = 0x140A;

    /** Light name */
    public static final int GL_LIGHT0 = 0x4000;
    public static final int GL_LIGHT1 = 0x4001;
    public static final int GL_LIGHT2 = 0x4002;
    public static final int GL_LIGHT3 = 0x4003;
    public static final int GL_LIGHT4 = 0x4004;
    public static final int GL_LIGHT5 = 0x4005;
    public static final int GL_LIGHT6 = 0x4006;
    public static final int GL_LIGHT7 = 0x4007;

    /** Light parameter */
    public static final int GL_AMBIENT               = 0x1200;
    public static final int GL_DIFFUSE               = 0x1201;
    public static final int GL_SPECULAR              = 0x1202;
    public static final int GL_POSITION              = 0x1203;
    public static final int GL_SPOT_DIRECTION        = 0x1204;
    public static final int GL_SPOT_EXPONENT         = 0x1205;
    public static final int GL_SPOT_CUTOFF           = 0x1206;
    public static final int GL_CONSTANT_ATTENUATION  = 0x1207;
    public static final int GL_LINEAR_ATTENUATION    = 0x1208;
    public static final int GL_QUADRATIC_ATTENUATION = 0x1209;

    /** List mode */
    public static final int GL_COMPILE             = 0x1300;
    public static final int GL_COMPILE_AND_EXECUTE = 0x1301;

    /** logic op */
    public static final int GL_CLEAR         = 0x1500;
    public static final int GL_AND           = 0x1501;
    public static final int GL_AND_REVERSE   = 0x1502;
    public static final int GL_COPY          = 0x1503;
    public static final int GL_AND_INVERTED  = 0x1504;
    public static final int GL_NOOP          = 0x1505;
    public static final int GL_XOR           = 0x1506;
    public static final int GL_OR            = 0x1507;
    public static final int GL_NOR           = 0x1508;
    public static final int GL_EQUIV         = 0x1509;
    public static final int GL_INVERT        = 0x150A;
    public static final int GL_OR_REVERSE    = 0x150B;
    public static final int GL_COPY_INVERTED = 0x150C;
    public static final int GL_OR_INVERTED   = 0x150D;
    public static final int GL_NAND          = 0x150E;
    public static final int GL_SET           = 0x150F;

    /** material parameter */
    public static final int GL_EMISSION            = 0x1600;
    public static final int GL_SHININESS           = 0x1601;
    public static final int GL_AMBIENT_AND_DIFFUSE = 0x1602;
    public static final int GL_COLOR_INDEXES       = 0x1603;

    /** matrix mode */
    public static final int GL_MODELVIEW  = 0x1700;
    public static final int GL_PROJECTION = 0x1701;
    public static final int GL_TEXTURE    = 0x1702;

    /** pixel copy type */
    public static final int GL_COLOR   = 0x1800;
    public static final int GL_DEPTH   = 0x1801;
    public static final int GL_STENCIL = 0x1802;

    /** pixel format */
    public static final int GL_COLOR_INDEX     = 0x1900;
    public static final int GL_STENCIL_INDEX   = 0x1901;
    public static final int GL_DEPTH_COMPONENT = 0x1902;
    public static final int GL_RED             = 0x1903;
    public static final int GL_GREEN           = 0x1904;
    public static final int GL_BLUE            = 0x1905;
    public static final int GL_ALPHA           = 0x1906;
    public static final int GL_RGB             = 0x1907;
    public static final int GL_RGBA            = 0x1908;
    public static final int GL_LUMINANCE       = 0x1909;
    public static final int GL_LUMINANCE_ALPHA = 0x190A;

    /** PixelType */
    public static final int GL_BITMAP = 0x1A00;

    /** PolygonMode */
    public static final int GL_POINT = 0x1B00;
    public static final int GL_LINE  = 0x1B01;
    public static final int GL_FILL  = 0x1B02;

    /** RenderMode */
    public static final int GL_RENDER   = 0x1C00;
    public static final int GL_FEEDBACK = 0x1C01;
    public static final int GL_SELECT   = 0x1C02;

    /** ShadingMode */
    public static final int GL_FLAT   = 0x1D00;
    public static final int GL_SMOOTH = 0x1D01;

    /** StencilOp */
    public static final int GL_KEEP    = 0x1E00;
    public static final int GL_REPLACE = 0x1E01;
    public static final int GL_INCR    = 0x1E02;
    public static final int GL_DECR    = 0x1E03;

    /** stringName */
    public static final int GL_VENDOR     = 0x1F00;
    public static final int GL_RENDERER   = 0x1F01;
    public static final int GL_VERSION    = 0x1F02;
    public static final int GL_EXTENSIONS = 0x1F03;

    /** TextureCoordName */
    public static final int GL_S = 0x2000;
    public static final int GL_T = 0x2001;
    public static final int GL_R = 0x2002;
    public static final int GL_Q = 0x2003;

    /** TextureEnvMode */
    public static final int GL_MODULATE = 0x2100;
    public static final int GL_DECAL    = 0x2101;

    /** TextureEnvParameter */
    public static final int GL_TEXTURE_ENV_MODE  = 0x2200;
    public static final int GL_TEXTURE_ENV_COLOR = 0x2201;

    /** TextureEnvTarget */
    public static final int GL_TEXTURE_ENV = 0x2300;

    /** TextureGenMode */
    public static final int GL_EYE_LINEAR    = 0x2400;
    public static final int GL_OBJECT_LINEAR = 0x2401;
    public static final int GL_SPHERE_MAP    = 0x2402;

    /** TextureGenParameter */
    public static final int GL_TEXTURE_GEN_MODE = 0x2500;
    public static final int GL_OBJECT_PLANE     = 0x2501;
    public static final int GL_EYE_PLANE        = 0x2502;

    /** TextureMagFilter */
    public static final int GL_NEAREST = 0x2600;
    public static final int GL_LINEAR  = 0x2601;

    /** TextureMinFilter */
    public static final int GL_NEAREST_MIPMAP_NEAREST = 0x2700;
    public static final int GL_LINEAR_MIPMAP_NEAREST  = 0x2701;
    public static final int GL_NEAREST_MIPMAP_LINEAR  = 0x2702;
    public static final int GL_LINEAR_MIPMAP_LINEAR   = 0x2703;

    /** TextureParameterName */
    public static final int GL_TEXTURE_MAG_FILTER = 0x2800;
    public static final int GL_TEXTURE_MIN_FILTER = 0x2801;
    public static final int GL_TEXTURE_WRAP_S     = 0x2802;
    public static final int GL_TEXTURE_WRAP_T     = 0x2803;

    /** TextureWrapMode */
    public static final int GL_CLAMP  = 0x2900;
    public static final int GL_REPEAT = 0x2901;

    /** ClientAttribMask */
    public static final int GL_CLIENT_PIXEL_STORE_BIT  = 0x1;
    public static final int GL_CLIENT_VERTEX_ARRAY_BIT = 0x2;
    public static final int GL_CLIENT_ALL_ATTRIB_BITS  = 0xFFFFFFFF;

    /** PolygonOffset */
    public static final int GL_POLYGON_OFFSET_FACTOR = 0x8038;
    public static final int GL_POLYGON_OFFSET_UNITS  = 0x2A00;
    public static final int GL_POLYGON_OFFSET_POINT  = 0x2A01;
    public static final int GL_POLYGON_OFFSET_LINE   = 0x2A02;
    public static final int GL_POLYGON_OFFSET_FILL   = 0x8037;

    /** Texture */
    public static final int GL_ALPHA4                 = 0x803B;
    public static final int GL_ALPHA8                 = 0x803C;
    public static final int GL_ALPHA12                = 0x803D;
    public static final int GL_ALPHA16                = 0x803E;
    public static final int GL_LUMINANCE4             = 0x803F;
    public static final int GL_LUMINANCE8             = 0x8040;
    public static final int GL_LUMINANCE12            = 0x8041;
    public static final int GL_LUMINANCE16            = 0x8042;
    public static final int GL_LUMINANCE4_ALPHA4      = 0x8043;
    public static final int GL_LUMINANCE6_ALPHA2      = 0x8044;
    public static final int GL_LUMINANCE8_ALPHA8      = 0x8045;
    public static final int GL_LUMINANCE12_ALPHA4     = 0x8046;
    public static final int GL_LUMINANCE12_ALPHA12    = 0x8047;
    public static final int GL_LUMINANCE16_ALPHA16    = 0x8048;
    public static final int GL_INTENSITY              = 0x8049;
    public static final int GL_INTENSITY4             = 0x804A;
    public static final int GL_INTENSITY8             = 0x804B;
    public static final int GL_INTENSITY12            = 0x804C;
    public static final int GL_INTENSITY16            = 0x804D;
    public static final int GL_R3_G3_B2               = 0x2A10;
    public static final int GL_RGB4                   = 0x804F;
    public static final int GL_RGB5                   = 0x8050;
    public static final int GL_RGB8                   = 0x8051;
    public static final int GL_RGB10                  = 0x8052;
    public static final int GL_RGB12                  = 0x8053;
    public static final int GL_RGB16                  = 0x8054;
    public static final int GL_RGBA2                  = 0x8055;
    public static final int GL_RGBA4                  = 0x8056;
    public static final int GL_RGB5_A1                = 0x8057;
    public static final int GL_RGBA8                  = 0x8058;
    public static final int GL_RGB10_A2               = 0x8059;
    public static final int GL_RGBA12                 = 0x805A;
    public static final int GL_RGBA16                 = 0x805B;
    public static final int GL_TEXTURE_RED_SIZE       = 0x805C;
    public static final int GL_TEXTURE_GREEN_SIZE     = 0x805D;
    public static final int GL_TEXTURE_BLUE_SIZE      = 0x805E;
    public static final int GL_TEXTURE_ALPHA_SIZE     = 0x805F;
    public static final int GL_TEXTURE_LUMINANCE_SIZE = 0x8060;
    public static final int GL_TEXTURE_INTENSITY_SIZE = 0x8061;
    public static final int GL_PROXY_TEXTURE_1D       = 0x8063;
    public static final int GL_PROXY_TEXTURE_2D       = 0x8064;

    /** TextureObject */
    public static final int GL_TEXTURE_PRIORITY   = 0x8066;
    public static final int GL_TEXTURE_RESIDENT   = 0x8067;
    public static final int GL_TEXTURE_BINDING_1D = 0x8068;
    public static final int GL_TEXTURE_BINDING_2D = 0x8069;

    /** VertexArray */
    public static final int GL_VERTEX_ARRAY                = 0x8074;
    public static final int GL_NORMAL_ARRAY                = 0x8075;
    public static final int GL_COLOR_ARRAY                 = 0x8076;
    public static final int GL_INDEX_ARRAY                 = 0x8077;
    public static final int GL_TEXTURE_COORD_ARRAY         = 0x8078;
    public static final int GL_EDGE_FLAG_ARRAY             = 0x8079;
    public static final int GL_VERTEX_ARRAY_SIZE           = 0x807A;
    public static final int GL_VERTEX_ARRAY_TYPE           = 0x807B;
    public static final int GL_VERTEX_ARRAY_STRIDE         = 0x807C;
    public static final int GL_NORMAL_ARRAY_TYPE           = 0x807E;
    public static final int GL_NORMAL_ARRAY_STRIDE         = 0x807F;
    public static final int GL_COLOR_ARRAY_SIZE            = 0x8081;
    public static final int GL_COLOR_ARRAY_TYPE            = 0x8082;
    public static final int GL_COLOR_ARRAY_STRIDE          = 0x8083;
    public static final int GL_INDEX_ARRAY_TYPE            = 0x8085;
    public static final int GL_INDEX_ARRAY_STRIDE          = 0x8086;
    public static final int GL_TEXTURE_COORD_ARRAY_SIZE    = 0x8088;
    public static final int GL_TEXTURE_COORD_ARRAY_TYPE    = 0x8089;
    public static final int GL_TEXTURE_COORD_ARRAY_STRIDE  = 0x808A;
    public static final int GL_EDGE_FLAG_ARRAY_STRIDE      = 0x808C;
    public static final int GL_VERTEX_ARRAY_POINTER        = 0x808E;
    public static final int GL_NORMAL_ARRAY_POINTER        = 0x808F;
    public static final int GL_COLOR_ARRAY_POINTER         = 0x8090;
    public static final int GL_INDEX_ARRAY_POINTER         = 0x8091;
    public static final int GL_TEXTURE_COORD_ARRAY_POINTER = 0x8092;
    public static final int GL_EDGE_FLAG_ARRAY_POINTER     = 0x8093;
    public static final int GL_V2F                         = 0x2A20;
    public static final int GL_V3F                         = 0x2A21;
    public static final int GL_C4UB_V2F                    = 0x2A22;
    public static final int GL_C4UB_V3F                    = 0x2A23;
    public static final int GL_C3F_V3F                     = 0x2A24;
    public static final int GL_N3F_V3F                     = 0x2A25;
    public static final int GL_C4F_N3F_V3F                 = 0x2A26;
    public static final int GL_T2F_V3F                     = 0x2A27;
    public static final int GL_T4F_V4F                     = 0x2A28;
    public static final int GL_T2F_C4UB_V3F                = 0x2A29;
    public static final int GL_T2F_C3F_V3F                 = 0x2A2A;
    public static final int GL_T2F_N3F_V3F                 = 0x2A2B;
    public static final int GL_T2F_C4F_N3F_V3F             = 0x2A2C;
    public static final int GL_T4F_C4F_N3F_V4F             = 0x2A2D;

    /** New token names. */
    public static final int GL_FOG_COORD_SRC                  = 0x8450;
    public static final int GL_FOG_COORD                      = 0x8451;
    public static final int GL_CURRENT_FOG_COORD              = 0x8453;
    public static final int GL_FOG_COORD_ARRAY_TYPE           = 0x8454;
    public static final int GL_FOG_COORD_ARRAY_STRIDE         = 0x8455;
    public static final int GL_FOG_COORD_ARRAY_POINTER        = 0x8456;
    public static final int GL_FOG_COORD_ARRAY                = 0x8457;
    public static final int GL_FOG_COORD_ARRAY_BUFFER_BINDING = 0x889D;
    public static final int GL_SRC0_RGB                       = 0x8580;
    public static final int GL_SRC1_RGB                       = 0x8581;
    public static final int GL_SRC2_RGB                       = 0x8582;
    public static final int GL_SRC0_ALPHA                     = 0x8588;
    public static final int GL_SRC1_ALPHA                     = 0x8589;
    public static final int GL_SRC2_ALPHA                     = 0x858A;

    /**
     * Accepted by the {@code target} parameters of BindBuffer, BufferData, BufferSubData, MapBuffer, UnmapBuffer, GetBufferSubData,
     * GetBufferParameteriv, and GetBufferPointerv.
     */
    public static final int GL_ARRAY_BUFFER         = 0x8892;
    public static final int GL_ELEMENT_ARRAY_BUFFER = 0x8893;

    /** Accepted by the {@code pname} parameter of GetBooleanv, GetIntegerv, GetFloatv, and GetDoublev. */
    public static final int GL_ARRAY_BUFFER_BINDING                 = 0x8894;
    public static final int GL_ELEMENT_ARRAY_BUFFER_BINDING         = 0x8895;
    public static final int GL_VERTEX_ARRAY_BUFFER_BINDING          = 0x8896;
    public static final int GL_NORMAL_ARRAY_BUFFER_BINDING          = 0x8897;
    public static final int GL_COLOR_ARRAY_BUFFER_BINDING           = 0x8898;
    public static final int GL_INDEX_ARRAY_BUFFER_BINDING           = 0x8899;
    public static final int GL_TEXTURE_COORD_ARRAY_BUFFER_BINDING   = 0x889A;
    public static final int GL_EDGE_FLAG_ARRAY_BUFFER_BINDING       = 0x889B;
    public static final int GL_SECONDARY_COLOR_ARRAY_BUFFER_BINDING = 0x889C;
    public static final int GL_FOG_COORDINATE_ARRAY_BUFFER_BINDING  = 0x889D;
    public static final int GL_WEIGHT_ARRAY_BUFFER_BINDING          = 0x889E;

    /** Accepted by the {@code pname} parameter of GetVertexAttribiv. */
    public static final int GL_VERTEX_ATTRIB_ARRAY_BUFFER_BINDING = 0x889F;

    /** Accepted by the {@code usage} parameter of BufferData. */
    public static final int GL_STREAM_DRAW  = 0x88E0;
    public static final int GL_STREAM_READ  = 0x88E1;
    public static final int GL_STREAM_COPY  = 0x88E2;
    public static final int GL_STATIC_DRAW  = 0x88E4;
    public static final int GL_STATIC_READ  = 0x88E5;
    public static final int GL_STATIC_COPY  = 0x88E6;
    public static final int GL_DYNAMIC_DRAW = 0x88E8;
    public static final int GL_DYNAMIC_READ = 0x88E9;
    public static final int GL_DYNAMIC_COPY = 0x88EA;

    /** Accepted by the {@code access} parameter of MapBuffer. */
    public static final int GL_READ_ONLY  = 0x88B8;
    public static final int GL_WRITE_ONLY = 0x88B9;
    public static final int GL_READ_WRITE = 0x88BA;

    /** Accepted by the {@code pname} parameter of GetBufferParameteriv. */
    public static final int GL_BUFFER_SIZE   = 0x8764;
    public static final int GL_BUFFER_USAGE  = 0x8765;
    public static final int GL_BUFFER_ACCESS = 0x88BB;
    public static final int GL_BUFFER_MAPPED = 0x88BC;

    /** Accepted by the {@code pname} parameter of GetBufferPointerv. */
    public static final int GL_BUFFER_MAP_POINTER = 0x88BD;

    /** Accepted by the {@code target} parameter of BeginQuery, EndQuery, and GetQueryiv. */
    public static final int GL_SAMPLES_PASSED = 0x8914;

    /** Accepted by the {@code pname} parameter of GetQueryiv. */
    public static final int GL_QUERY_COUNTER_BITS = 0x8864;
    public static final int GL_CURRENT_QUERY      = 0x8865;

    /** Accepted by the {@code pname} parameter of GetQueryObjectiv and GetQueryObjectuiv. */
    public static final int GL_QUERY_RESULT           = 0x8866;
    public static final int GL_QUERY_RESULT_AVAILABLE = 0x8867;

    /** Accepted by the {@code type} argument of CreateShader and returned by the {@code params} parameter of GetShaderiv. */
    public static final int GL_FRAGMENT_SHADER = 0x8B30;
    public static final int GL_VERTEX_SHADER   = 0x8B31;

    /** Accepted by the {@code pname} parameter of GetBooleanv, GetIntegerv, GetFloatv, and GetDoublev. */
    public static final int GL_MAX_FRAGMENT_UNIFORM_COMPONENTS = 0x8B49;

    /** Accepted by the {@code target} parameter of Hint and the {@code pname} parameter of GetBooleanv, GetIntegerv, GetFloatv, and GetDoublev. */
    public static final int GL_FRAGMENT_SHADER_DERIVATIVE_HINT = 0x8B8B;

    /** Accepted by the {@code name} parameter of GetString. */
    public static final int GL_SHADING_LANGUAGE_VERSION = 0x8B8C;

    /** Accepted by the {@code pname} parameter of GetInteger. */
    public static final int GL_CURRENT_PROGRAM = 0x8B8D;

    /** accepted by the {@code pname} parameter of GetShaderiv */
    public static final int GL_SHADER_TYPE                 = 0x8B4F;
    public static final int GL_DELETE_STATUS               = 0x8B80;
    public static final int GL_COMPILE_STATUS              = 0x8B81;
    public static final int GL_LINK_STATUS                 = 0x8B82;
    public static final int GL_VALIDATE_STATUS             = 0x8B83;
    public static final int GL_INFO_LOG_LENGTH             = 0x8B84;
    public static final int GL_ATTACHED_SHADERS            = 0x8B85;
    public static final int GL_ACTIVE_UNIFORM_MAX_LENGTH   = 0x8B87;
    public static final int GL_ACTIVE_ATTRIBUTES           = 0x8B89;
    public static final int GL_ACTIVE_ATTRIBUTE_MAX_LENGTH = 0x8B8A;
    public static final int GL_SHADER_SOURCE_LENGTH        = 0x8B88;

    /** Returned by the {@code type} parameter of GetActiveUniform. */
    public static final int GL_FLOAT_VEC2        = 0x8B50;
    public static final int GL_FLOAT_VEC3        = 0x8B51;
    public static final int GL_FLOAT_VEC4        = 0x8B52;
    public static final int GL_INT_VEC2          = 0x8B53;
    public static final int GL_INT_VEC3          = 0x8B54;
    public static final int GL_INT_VEC4          = 0x8B55;
    public static final int GL_BOOL              = 0x8B56;
    public static final int GL_BOOL_VEC2         = 0x8B57;
    public static final int GL_BOOL_VEC3         = 0x8B58;
    public static final int GL_BOOL_VEC4         = 0x8B59;
    public static final int GL_FLOAT_MAT2        = 0x8B5A;
    public static final int GL_FLOAT_MAT3        = 0x8B5B;
    public static final int GL_FLOAT_MAT4        = 0x8B5C;
    public static final int GL_SAMPLER_1D        = 0x8B5D;
    public static final int GL_SAMPLER_2D        = 0x8B5E;
    public static final int GL_SAMPLER_3D        = 0x8B5F;
    public static final int GL_SAMPLER_CUBE      = 0x8B60;
    public static final int GL_SAMPLER_1D_SHADOW = 0x8B61;
    public static final int GL_SAMPLER_2D_SHADOW = 0x8B62;

    /** Accepted by the {@code pname} parameter of GetBooleanv, GetIntegerv, GetFloatv, and GetDoublev. */
    public static final int GL_MAX_VERTEX_UNIFORM_COMPONENTS    = 0x8B4A;
    public static final int GL_MAX_VARYING_FLOATS               = 0x8B4B;
    public static final int GL_MAX_VERTEX_ATTRIBS               = 0x8869;
    public static final int GL_MAX_TEXTURE_IMAGE_UNITS          = 0x8872;
    public static final int GL_MAX_VERTEX_TEXTURE_IMAGE_UNITS   = 0x8B4C;
    public static final int GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS = 0x8B4D;
    public static final int GL_MAX_TEXTURE_COORDS               = 0x8871;

    /**
     * Accepted by the {@code cap} parameter of Disable, Enable, and IsEnabled, and by the {@code pname} parameter of GetBooleanv, GetIntegerv, GetFloatv, and
     * GetDoublev.
     */
    public static final int GL_VERTEX_PROGRAM_POINT_SIZE = 0x8642;
    public static final int GL_VERTEX_PROGRAM_TWO_SIDE   = 0x8643;

    /** Accepted by the {@code pname} parameter of GetVertexAttrib{dfi}v. */
    public static final int GL_VERTEX_ATTRIB_ARRAY_ENABLED    = 0x8622;
    public static final int GL_VERTEX_ATTRIB_ARRAY_SIZE       = 0x8623;
    public static final int GL_VERTEX_ATTRIB_ARRAY_STRIDE     = 0x8624;
    public static final int GL_VERTEX_ATTRIB_ARRAY_TYPE       = 0x8625;
    public static final int GL_VERTEX_ATTRIB_ARRAY_NORMALIZED = 0x886A;
    public static final int GL_CURRENT_VERTEX_ATTRIB          = 0x8626;

    /** Accepted by the {@code pname} parameter of GetVertexAttribPointerv. */
    public static final int GL_VERTEX_ATTRIB_ARRAY_POINTER = 0x8645;

    /** Accepted by the {@code pname} parameters of GetIntegerv, GetFloatv, and GetDoublev. */
    public static final int GL_MAX_DRAW_BUFFERS = 0x8824;
    public static final int GL_DRAW_BUFFER0     = 0x8825;
    public static final int GL_DRAW_BUFFER1     = 0x8826;
    public static final int GL_DRAW_BUFFER2     = 0x8827;
    public static final int GL_DRAW_BUFFER3     = 0x8828;
    public static final int GL_DRAW_BUFFER4     = 0x8829;
    public static final int GL_DRAW_BUFFER5     = 0x882A;
    public static final int GL_DRAW_BUFFER6     = 0x882B;
    public static final int GL_DRAW_BUFFER7     = 0x882C;
    public static final int GL_DRAW_BUFFER8     = 0x882D;
    public static final int GL_DRAW_BUFFER9     = 0x882E;
    public static final int GL_DRAW_BUFFER10    = 0x882F;
    public static final int GL_DRAW_BUFFER11    = 0x8830;
    public static final int GL_DRAW_BUFFER12    = 0x8831;
    public static final int GL_DRAW_BUFFER13    = 0x8832;
    public static final int GL_DRAW_BUFFER14    = 0x8833;
    public static final int GL_DRAW_BUFFER15    = 0x8834;

    /**
     * Accepted by the {@code cap} parameter of Enable, Disable, and IsEnabled, by the {@code pname} parameter of GetBooleanv, GetIntegerv, GetFloatv, and
     * GetDoublev, and by the {@code target} parameter of TexEnvi, TexEnviv, TexEnvf, TexEnvfv, GetTexEnviv, and GetTexEnvfv.
     */
    public static final int GL_POINT_SPRITE = 0x8861;

    /**
     * When the {@code target} parameter of TexEnvf, TexEnvfv, TexEnvi, TexEnviv, GetTexEnvfv, or GetTexEnviv is POINT_SPRITE, then the value of
     * {@code pname} may be.
     */
    public static final int GL_COORD_REPLACE = 0x8862;

    /** Accepted by the {@code pname} parameter of PointParameter{if}v. */
    public static final int GL_POINT_SPRITE_COORD_ORIGIN = 0x8CA0;

    /** Accepted by the {@code param} parameter of PointParameter{if}v. */
    public static final int GL_LOWER_LEFT = 0x8CA1;
    public static final int GL_UPPER_LEFT = 0x8CA2;

    /** Accepted by the {@code pname} parameter of GetBooleanv, GetIntegerv, GetFloatv, and GetDoublev. */
    public static final int GL_BLEND_EQUATION_RGB   = 0x8009;
    public static final int GL_BLEND_EQUATION_ALPHA = 0x883D;

    /** Accepted by the {@code pname} parameter of GetIntegerv. */
    public static final int GL_STENCIL_BACK_FUNC            = 0x8800;
    public static final int GL_STENCIL_BACK_FAIL            = 0x8801;
    public static final int GL_STENCIL_BACK_PASS_DEPTH_FAIL = 0x8802;
    public static final int GL_STENCIL_BACK_PASS_DEPTH_PASS = 0x8803;
    public static final int GL_STENCIL_BACK_REF             = 0x8CA3;
    public static final int GL_STENCIL_BACK_VALUE_MASK      = 0x8CA4;
    public static final int GL_STENCIL_BACK_WRITEMASK       = 0x8CA5;


    int createShader(int shaderType) throws Throwable;

    void shaderSource(int shader, MemorySegment source) throws Throwable;

    void compileShader(int shader) throws Throwable;

    void getShaderiv(int shader, int pname, MemoryAddress success) throws Throwable;

    void getShaderInfoLog(int shader, int maxLength, MemoryAddress length, MemoryAddress infoLog) throws Throwable;

    void deleteShader(int shader) throws Throwable;

    int createProgram() throws Throwable;

    void attachShader(int program, int shader) throws Throwable;

    void linkProgram(int program) throws Throwable;

    void getProgramIv(int program, int pname, MemoryAddress success) throws Throwable;

    void getProgramInfoLog(int program, int maxlength, MemoryAddress length, MemoryAddress infoLog) throws Throwable;

    void useProgram(int program) throws Throwable;

    void clear(int bitmask) throws Throwable;

    void generateVertexArrays(int number, MemoryAddress array) throws Throwable;

    void bindVertexArray(int vao) throws Throwable;

    void generateBuffers(int numbers, MemoryAddress array) throws Throwable;

    void bindBuffers(int target, int vbo) throws Throwable;

    int isBuffer(int vbo) throws Throwable;

    void vertexAttribPointer(int index, int size, int type, int normalized, int stride, MemoryAddress pointer) throws Throwable;

    void enableVertexAttribArray(int index) throws Throwable;

    void bufferData(int target, int size, MemoryAddress data, int mode) throws Throwable;

    void bufferSubData(int target, int offset, int size, MemoryAddress data) throws Throwable;

    void deleteBuffers(int n, MemoryAddress buffers) throws Throwable;
}
