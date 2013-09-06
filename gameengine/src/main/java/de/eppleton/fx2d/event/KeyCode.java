package de.eppleton.fx2d.event;

import java.util.HashMap;
import java.util.Map;

public enum KeyCode {

    ENTER(0x0A, "Enter", KeyCodeClass.WHITESPACE),
    BACK_SPACE(0x08, "Backspace"),
    TAB(0x09, "Tab", KeyCodeClass.WHITESPACE),
    CANCEL(0x03, "Cancel"),
    CLEAR(0x0C, "Clear"),
    SHIFT(0x10, "Shift", KeyCodeClass.MODIFIER),
    CONTROL(0x11, "Ctrl", KeyCodeClass.MODIFIER),
    ALT(0x12, "Alt", KeyCodeClass.MODIFIER),
    PAUSE(0x13, "Pause"),
    CAPS(0x14, "Caps Lock"),
    ESCAPE(0x1B, "Esc"),
    SPACE(0x20, "Space", KeyCodeClass.WHITESPACE),
    PAGE_UP(0x21, "Page Up", KeyCodeClass.NAVIGATION),
    PAGE_DOWN(0x22, "Page Down", KeyCodeClass.NAVIGATION),
    END(0x23, "End", KeyCodeClass.NAVIGATION),
    HOME(0x24, "Home", KeyCodeClass.NAVIGATION),
    LEFT(0x25, "Left", KeyCodeClass.ARROW | KeyCodeClass.NAVIGATION),
    UP(0x26, "Up", KeyCodeClass.ARROW | KeyCodeClass.NAVIGATION),
    RIGHT(0x27, "Right", KeyCodeClass.ARROW | KeyCodeClass.NAVIGATION),
    DOWN(0x28, "Down", KeyCodeClass.ARROW | KeyCodeClass.NAVIGATION),
    COMMA(0x2C, "Comma"),
    MINUS(0x2D, "Minus"),
    PERIOD(0x2E, "Period"),
    SLASH(0x2F, "Slash"),
    DIGIT0(0x30, "0", KeyCodeClass.DIGIT),
    DIGIT1(0x31, "1", KeyCodeClass.DIGIT),
    DIGIT2(0x32, "2", KeyCodeClass.DIGIT),
    DIGIT3(0x33, "3", KeyCodeClass.DIGIT),
    DIGIT4(0x34, "4", KeyCodeClass.DIGIT),
    DIGIT5(0x35, "5", KeyCodeClass.DIGIT),
    DIGIT6(0x36, "6", KeyCodeClass.DIGIT),
    DIGIT7(0x37, "7", KeyCodeClass.DIGIT),
    DIGIT8(0x38, "8", KeyCodeClass.DIGIT),
    DIGIT9(0x39, "9", KeyCodeClass.DIGIT),
    SEMICOLON(0x3B, "Semicolon"),
    EQUALS(0x3D, "Equals"),
    A(0x41, "A", KeyCodeClass.LETTER),
    B(0x42, "B", KeyCodeClass.LETTER),
    C(0x43, "C", KeyCodeClass.LETTER),
    D(0x44, "D", KeyCodeClass.LETTER),
    E(0x45, "E", KeyCodeClass.LETTER),
    F(0x46, "F", KeyCodeClass.LETTER),
    G(0x47, "G", KeyCodeClass.LETTER),
    H(0x48, "H", KeyCodeClass.LETTER),
    I(0x49, "I", KeyCodeClass.LETTER),
    J(0x4A, "J", KeyCodeClass.LETTER),
    K(0x4B, "K", KeyCodeClass.LETTER),
    L(0x4C, "L", KeyCodeClass.LETTER),
    M(0x4D, "M", KeyCodeClass.LETTER),
    N(0x4E, "N", KeyCodeClass.LETTER),
    O(0x4F, "O", KeyCodeClass.LETTER),
    P(0x50, "P", KeyCodeClass.LETTER),
    Q(0x51, "Q", KeyCodeClass.LETTER),
    R(0x52, "R", KeyCodeClass.LETTER),
    S(0x53, "S", KeyCodeClass.LETTER),
    T(0x54, "T", KeyCodeClass.LETTER),
    U(0x55, "U", KeyCodeClass.LETTER),
    V(0x56, "V", KeyCodeClass.LETTER),
    W(0x57, "W", KeyCodeClass.LETTER),
    X(0x58, "X", KeyCodeClass.LETTER),
    Y(0x59, "Y", KeyCodeClass.LETTER),
    Z(0x5A, "Z", KeyCodeClass.LETTER),
    OPEN_BRACKET(0x5B, "Open Bracket"),
    BACK_SLASH(0x5C, "Back Slash"),
    CLOSE_BRACKET(0x5D, "Close Bracket"),
    NUMPAD0(0x60, "Numpad 0", KeyCodeClass.DIGIT | KeyCodeClass.KEYPAD),
    NUMPAD1(0x61, "Numpad 1", KeyCodeClass.DIGIT | KeyCodeClass.KEYPAD),
    NUMPAD2(0x62, "Numpad 2", KeyCodeClass.DIGIT | KeyCodeClass.KEYPAD),
    NUMPAD3(0x63, "Numpad 3", KeyCodeClass.DIGIT | KeyCodeClass.KEYPAD),
    NUMPAD4(0x64, "Numpad 4", KeyCodeClass.DIGIT | KeyCodeClass.KEYPAD),
    NUMPAD5(0x65, "Numpad 5", KeyCodeClass.DIGIT | KeyCodeClass.KEYPAD),
    NUMPAD6(0x66, "Numpad 6", KeyCodeClass.DIGIT | KeyCodeClass.KEYPAD),
    NUMPAD7(0x67, "Numpad 7", KeyCodeClass.DIGIT | KeyCodeClass.KEYPAD),
    NUMPAD8(0x68, "Numpad 8", KeyCodeClass.DIGIT | KeyCodeClass.KEYPAD),
    NUMPAD9(0x69, "Numpad 9", KeyCodeClass.DIGIT | KeyCodeClass.KEYPAD),
    MULTIPLY(0x6A, "Multiply"),
    ADD(0x6B, "Add"),
    SEPARATOR(0x6C, "Separator"),
    SUBTRACT(0x6D, "Subtract"),
    DECIMAL(0x6E, "Decimal"),
    DIVIDE(0x6F, "Divide"),
    DELETE(0x7F, "Delete"), /* ASCII:Integer   DEL */
    NUM_LOCK(0x90, "Num Lock"),
    SCROLL_LOCK(0x91, "Scroll Lock"),
    F1(0x70, "F1", KeyCodeClass.FUNCTION),
    F2(0x71, "F2", KeyCodeClass.FUNCTION),
    F3(0x72, "F3", KeyCodeClass.FUNCTION),
    F4(0x73, "F4", KeyCodeClass.FUNCTION),
    F5(0x74, "F5", KeyCodeClass.FUNCTION),
    F6(0x75, "F6", KeyCodeClass.FUNCTION),
    F7(0x76, "F7", KeyCodeClass.FUNCTION),
    F8(0x77, "F8", KeyCodeClass.FUNCTION),
    F9(0x78, "F9", KeyCodeClass.FUNCTION),
    F10(0x79, "F10", KeyCodeClass.FUNCTION),
    F11(0x7A, "F11", KeyCodeClass.FUNCTION),
    F12(0x7B, "F12", KeyCodeClass.FUNCTION),
    F13(0xF000, "F13", KeyCodeClass.FUNCTION),
    F14(0xF001, "F14", KeyCodeClass.FUNCTION),
    F15(0xF002, "F15", KeyCodeClass.FUNCTION),
    F16(0xF003, "F16", KeyCodeClass.FUNCTION),
    F17(0xF004, "F17", KeyCodeClass.FUNCTION),
    F18(0xF005, "F18", KeyCodeClass.FUNCTION),
    F19(0xF006, "F19", KeyCodeClass.FUNCTION),
    F20(0xF007, "F20", KeyCodeClass.FUNCTION),
    F21(0xF008, "F21", KeyCodeClass.FUNCTION),
    F22(0xF009, "F22", KeyCodeClass.FUNCTION),
    F23(0xF00A, "F23", KeyCodeClass.FUNCTION),
    F24(0xF00B, "F24", KeyCodeClass.FUNCTION),
    PRINTSCREEN(0x9A, "Print Screen"),
    INSERT(0x9B, "Insert"),
    HELP(0x9C, "Help"),
    META(0x9D, "Meta", KeyCodeClass.MODIFIER),
    BACK_QUOTE(0xC0, "Back Quote"),
    QUOTE(0xDE, "Quote"),
    KP_UP(0xE0, "Numpad Up", KeyCodeClass.ARROW | KeyCodeClass.NAVIGATION | KeyCodeClass.KEYPAD),
    KP_DOWN(0xE1, "Numpad Down", KeyCodeClass.ARROW | KeyCodeClass.NAVIGATION | KeyCodeClass.KEYPAD),
    KP_LEFT(0xE2, "Numpad Left", KeyCodeClass.ARROW | KeyCodeClass.NAVIGATION | KeyCodeClass.KEYPAD),
    KP_RIGHT(0xE3, "Numpad Right", KeyCodeClass.ARROW | KeyCodeClass.NAVIGATION | KeyCodeClass.KEYPAD),
    DEAD_GRAVE(0x80, "Dead Grave"),
    DEAD_ACUTE(0x81, "Dead Acute"),
    DEAD_CIRCUMFLEX(0x82, "Circumflex"),
    DEAD_TILDE(0x83, "Dead Tilde"),
    DEAD_MACRON(0x84, "Dead Macron"),
    DEAD_BREVE(0x85, "Dead Breve"),
    DEAD_ABOVEDOT(0x86, "Dead Abovedot"),
    DEAD_DIAERESIS(0x87, "Dead Diaeresis"),
    DEAD_ABOVERING(0x88, "Dead Abovering"),
    DEAD_DOUBLEACUTE(0x89, "Dead Doubleacute"),
    DEAD_CARON(0x8a, "Dead Caron"),
    DEAD_CEDILLA(0x8b, "Dead Cedilla"),
    DEAD_OGONEK(0x8c, "Dead Ogonek"),
    DEAD_IOTA(0x8d, "Dead Iota"),
    DEAD_VOICED_SOUND(0x8e, "Dead Voiced Sound"),
    DEAD_SEMIVOICED_SOUND(0x8f, "Dead Semivoiced Sound"),
    AMPERSAND(0x96, "Ampersand"),
    ASTERISK(0x97, "Asterisk"),
    QUOTEDBL(0x98, "Double Quote"),
    LESS(0x99, "Less"),
    GREATER(0xa0, "Greater"),
    BRACELEFT(0xa1, "Left Brace"),
    BRACERIGHT(0xa2, "Right Brace"),
    AT(0x0200, "At"),
    COLON(0x0201, "Colon"),
    CIRCUMFLEX(0x0202, "Circumflex"),
    DOLLAR(0x0203, "Dollar"),
    EURO_SIGN(0x0204, "Euro Sign"),
    EXCLAMATION_MARK(0x0205, "Exclamation Mark"),
    INVERTED_EXCLAMATION_MARK(0x0206, "Inverted Exclamation Mark"),
    LEFT_PARENTHESIS(0x0207, "Left Parenthesis"),
    NUMBER_SIGN(0x0208, "Number Sign"),
    PLUS(0x0209, "Plus"),
    RIGHT_PARENTHESIS(0x020A, "Right Parenthesis"),
    UNDERSCORE(0x020B, "Underscore"),
    WINDOWS(0x020C, "Windows", KeyCodeClass.MODIFIER),
    CONTEXT_MENU(0x020D, "Context Menu"),
    FINAL(0x0018, "Final"),
    CONVERT(0x001C, "Convert"),
    NONCONVERT(0x001D, "Nonconvert"),
    ACCEPT(0x001E, "Accept"),
    MODECHANGE(0x001F, "Mode Change"),
    KANA(0x0015, "Kana"),
    KANJI(0x0019, "Kanji"),
    ALPHANUMERIC(0x00F0, "Alphanumeric"),
    KATAKANA(0x00F1, "Katakana"),
    HIRAGANA(0x00F2, "Hiragana"),
    FULL_WIDTH(0x00F3, "Full Width"),
    HALF_WIDTH(0x00F4, "Half Width"),
    ROMAN_CHARACTERS(0x00F5, "Roman Characters"),
    ALL_CANDIDATES(0x0100, "All Candidates"),
    PREVIOUS_CANDIDATE(0x0101, "Previous Candidate"),
    CODE_INPUT(0x0102, "Code Input"),
    JAPANESE_KATAKANA(0x0103, "Japanese Katakana"),
    JAPANESE_HIRAGANA(0x0104, "Japanese Hiragana"),
    JAPANESE_ROMAN(0x0105, "Japanese Roman"),
    KANA_LOCK(0x0106, "Kana Lock"),
    INPUT_METHOD_ON_OFF(0x0107, "Input Method On/Off"),
    CUT(0xFFD1, "Cut"),
    COPY(0xFFCD, "Copy"),
    PASTE(0xFFCF, "Paste"),
    UNDO(0xFFCB, "Undo"),
    AGAIN(0xFFC9, "Again"),
    FIND(0xFFD0, "Find"),
    PROPS(0xFFCA, "Properties"),
    STOP(0xFFC8, "Stop"),
    COMPOSE(0xFF20, "Compose"),
    ALT_GRAPH(0xFF7E, "Alt Graph", KeyCodeClass.MODIFIER),
    BEGIN(0xFF58, "Begin"),
    UNDEFINED(0x0, "Undefined"),
    //--------------------------------------------------------------
    //
    // Mobile and Embedded Specific Key Codes
    //
    //--------------------------------------------------------------

    SOFTKEY_0(0x1000, "Softkey 0"),
    SOFTKEY_1(0x1001, "Softkey 1"),
    SOFTKEY_2(0x1002, "Softkey 2"),
    SOFTKEY_3(0x1003, "Softkey 3"),
    SOFTKEY_4(0x1004, "Softkey 4"),
    SOFTKEY_5(0x1005, "Softkey 5"),
    SOFTKEY_6(0x1006, "Softkey 6"),
    SOFTKEY_7(0x1007, "Softkey 7"),
    SOFTKEY_8(0x1008, "Softkey 8"),
    SOFTKEY_9(0x1009, "Softkey 9"),
    GAME_A(0x100A, "Game A"),
    GAME_B(0x100B, "Game B"),
    GAME_C(0x100C, "Game C"),
    GAME_D(0x100D, "Game D"),
    STAR(0x100E, "Star"),
    POUND(0x100F, "Pound"),
    POWER(0x199, "Power"),
    INFO(0x1C9, "Info"),
    COLORED_KEY_0(0x193, "Colored Key 0"),
    COLORED_KEY_1(0x194, "Colored Key 1"),
    COLORED_KEY_2(0x195, "Colored Key 2"),
    COLORED_KEY_3(0x196, "Colored Key 3"),
    EJECT_TOGGLE(0x19E, "Eject", KeyCodeClass.MEDIA),
    PLAY(0x19F, "Play", KeyCodeClass.MEDIA),
    RECORD(0x1A0, "Record", KeyCodeClass.MEDIA),
    FAST_FWD(0x1A1, "Fast Forward", KeyCodeClass.MEDIA),
    REWIND(0x19C, "Rewind", KeyCodeClass.MEDIA),
    TRACK_PREV(0x1A8, "Previous Track", KeyCodeClass.MEDIA),
    TRACK_NEXT(0x1A9, "Next Track", KeyCodeClass.MEDIA),
    CHANNEL_UP(0x1AB, "Channel Up", KeyCodeClass.MEDIA),
    CHANNEL_DOWN(0x1AC, "Channel Down", KeyCodeClass.MEDIA),
    VOLUME_UP(0x1bf, "Volume Up", KeyCodeClass.MEDIA),
    VOLUME_DOWN(0x1C0, "Volume Down", KeyCodeClass.MEDIA),
    MUTE(0x1C1, "Mute", KeyCodeClass.MEDIA),
    COMMAND(0x300, "Command", KeyCodeClass.MODIFIER),
    SHORTCUT(-1, "Shortcut");
    final int code;
    final String ch;
    final String name;
    private int mask;

    private static class KeyCodeClass {

        private KeyCodeClass() {
        }
        ;

        private static final int FUNCTION = 1;
        private static final int NAVIGATION = 1 << 1;
        private static final int ARROW = 1 << 2;
        private static final int MODIFIER = 1 << 3;
        private static final int LETTER = 1 << 4;
        private static final int DIGIT = 1 << 5;
        private static final int KEYPAD = 1 << 6;
        private static final int WHITESPACE = 1 << 7;
        private static final int MEDIA = 1 << 8;
    }

    private KeyCode(int code, String name, int mask) {
        this.code = code;
        this.name = name;
        this.mask = mask;
        ch = String.valueOf((char) code);
    }

    private KeyCode(int code, String name) {
        this(code, name, 0);
    }

    public final boolean isFunctionKey() {
        return (mask & KeyCodeClass.FUNCTION) != 0;
    }

    public final boolean isNavigationKey() {
        return (mask & KeyCodeClass.NAVIGATION) != 0;
    }

    public final boolean isArrowKey() {
        return (mask & KeyCodeClass.ARROW) != 0;
    }

    public final boolean isModifierKey() {
        return (mask & KeyCodeClass.MODIFIER) != 0;
    }

    public final boolean isLetterKey() {
        return (mask & KeyCodeClass.LETTER) != 0;
    }

    public final boolean isDigitKey() {
        return (mask & KeyCodeClass.DIGIT) != 0;
    }

    public final boolean isKeypadKey() {
        return (mask & KeyCodeClass.KEYPAD) != 0;
    }

    public final boolean isWhitespaceKey() {
        return (mask & KeyCodeClass.WHITESPACE) != 0;
    }

    public final boolean isMediaKey() {
        return (mask & KeyCodeClass.MEDIA) != 0;
    }

    public final String getName() {
        return name;
    }

    public String impl_getChar() {
        return ch;
    }

    public int getCode() {
        return code;
    }
    private static final Map<String, KeyCode> nameMap;
    private static final Map<Integer, KeyCode> codeMap;

    static {

        nameMap = new HashMap<String, KeyCode>(KeyCode.values().length);
        for (KeyCode c : KeyCode.values()) {
            nameMap.put(c.name, c);
        }
        codeMap = new HashMap<Integer, KeyCode>(KeyCode.values().length);
        for (KeyCode c : KeyCode.values()) {
            codeMap.put(c.getCode(), c);
        }
    }

    public static KeyCode getKeyCode(String name) {
        return nameMap.get(name);
    }

    public static KeyCode getKeyCode(Integer ascii) {
        return codeMap.get(ascii);
    }
}
