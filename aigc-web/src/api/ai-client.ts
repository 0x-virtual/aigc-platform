import CryptoJS from 'crypto-js';

export const secretKey = '1';

export function decrypt(text: unknown, key: string) {
  if (typeof text === 'string') {
    const textParts = text.split(':');
    const iv = CryptoJS.enc.Hex.parse(textParts.shift());
    const encryptedText = CryptoJS.enc.Hex.parse(textParts.join(':'));
    const decrypted = CryptoJS.AES.decrypt(
      { ciphertext: encryptedText },
      CryptoJS.enc.Utf8.parse(key),
      {
        iv,
        mode: CryptoJS.mode.CBC,
        padding: CryptoJS.pad.Pkcs7,
      }
    );
    return decrypted.toString(CryptoJS.enc.Utf8);
  }
  return text;
}
// AES 加密函数
export function encrypt(text: string, key: string) {
    const iv = CryptoJS.lib.WordArray.random(16);
    const encrypted = CryptoJS.AES.encrypt(text, CryptoJS.enc.Utf8.parse(key), {
        iv,
        mode: CryptoJS.mode.CBC,
        padding: CryptoJS.pad.Pkcs7,
    });
    return `${iv.toString()}:${  encrypted.ciphertext.toString()}`;
}
