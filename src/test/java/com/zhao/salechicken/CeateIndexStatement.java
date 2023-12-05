package com.zhao.salechicken;

/**
 * @Author: 小赵
 * @DateTime: 2023/11/25 14:18
 */
public class CeateIndexStatement {

    public static String CREATE_PRODUCT_INDEX = "{\n" +
            "  \"settings\": {\n" +
            "    \"analysis\": {\n" +
            "      \"analyzer\": {\n" +
            "        \"text_anlyzer\": {\n" +
            "          \"tokenizer\": \"ik_max_word\",\n" +
            "          \"filter\": \"py\"\n" +
            "        },\n" +
            "        \"completion_analyzer\": {\n" +
            "          \"tokenizer\": \"keyword\",\n" +
            "          \"filter\": \"py\"\n" +
            "        }\n" +
            "      },\n" +
            "      \"filter\": {\n" +
            "        \"py\": {\n" +
            "          \"type\": \"pinyin\",\n" +
            "          \"keep_full_pinyin\": false,\n" +
            "          \"keep_joined_full_pinyin\": true,\n" +
            "          \"keep_original\": true,\n" +
            "          \"limit_first_letter_length\": 16,\n" +
            "          \"remove_duplicated_term\": true,\n" +
            "          \"none_chinese_pinyin_tokenize\": false\n" +
            "        }\n" +
            "      }\n" +
            "    }\n" +
            "  },\n" +
            "  \"mappings\": {\n" +
            "    \"properties\": {\n" +
            "      \"productId\":{\n" +
            "        \"type\": \"keyword\"\n" +
            "      },\n" +
            "      \"productName\":{\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"text_anlyzer\",\n" +
            "        \"search_analyzer\": \"ik_smart\",\n" +
            "        \"copy_to\": \"all\"\n" +
            "      },\n" +
            "      \"description\":{\n" +
            "        \"type\": \"text\",\n" +
            "        \"index\": false\n" +
            "      },\n" +
            "      \"price\":{\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"weight\":{\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"origin\":{\n" +
            "        \"type\": \"keyword\",\n" +
            "        \"copy_to\": \"all\"\n" +
            "      },\n" +
            "      \"category\":{\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"inventory\":{\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"image\":{\n" +
            "        \"type\": \"text\",\n" +
            "        \"index\": false\n" +
            "      },\n" +
            "      \"sales\":{\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"all\":{\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"text_anlyzer\",\n" +
            "        \"search_analyzer\": \"ik_smart\"\n" +
            "      },\n" +
            "      \"suggestion\":{\n" +
            "          \"type\": \"completion\",\n" +
            "          \"analyzer\": \"completion_analyzer\"\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}\n";
}
