import keyBy from 'lodash/keyBy';

export const i18nLanguages = [
    {
        name: '不翻译',
        value: 'no_translate',
    },
    {
        name: '英文',
        value: 'en_US',
    },
    {
        name: '中文',
        value: 'zh_CN',
    },
]


export const languageMap = keyBy(i18nLanguages, "value")

export function convertLanguageDisplay(lang: string) {
    return languageMap[lang]?.name || '不翻译'
}
