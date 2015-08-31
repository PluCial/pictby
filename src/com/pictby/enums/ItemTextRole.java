package com.pictby.enums;

/**
 * テキストリソース役割
 * <pre>
 * 追加や変更の場合は必ずTextResourcesService 内のUpdateも合わせて修正
 * </pre>
 * @author takahara
 *
 */
public enum ItemTextRole {
    ITEM_NAME("画像名", false),
    ITEM_DETAIL("画像説明", true);

    private String name;
    
    /**
     * 編集時にテキストフィールドを表示するか、テキストエリアを表示するかをこのパラメーターで切り替える
     */
    private boolean longText;

    private ItemTextRole(String name, boolean longText) {
        this.name = name;
        this.longText = longText;
    }

    public String getName() {
        return name;
    }

    public boolean isLongText() {
        return longText;
    }
}
