#ifndef _HILL_
#define _HILL_

/* Hill Cipher Implementation
 * Implemented at June 10, 2018 with C/C++
 */

namespace Hill {
    using i32 = int;
    using u32 = unsigned;

    inline void SWAP(i32* a, i32* b) {
        i32 tmp = *a;
        *a = *b;
        *b = tmp;
    }

    inline i32 ABS(const i32 x) {
        return x < 0 ? -x : x;
    }

    inline i32 NORMALIZE(const i32 x, const i32 v) {
        return x < 0 ? v - ABS(x) : x;
    }

    
    i32 EUCLEDEAN_GCD(const i32 a, const i32 b) {
        if (b == 0) 
            return a;
        
        return EUCLEDEAN_GCD(b, a % b);
    }

    bool HILL_KEY_AVAILABLE(i32 const key[][2]) {
        return key && 
            ABS(
                EUCLEDEAN_GCD(key[0][0] * key[1][1] - key[0][1] * key[1][0], 26)
                )== 1;
    }

    i32* HILL_ENCRYPT(i32 const plain[2], i32 const key[][2]) {
        if (!plain && !HILL_KEY_AVAILABLE(key)) {
            return NULL;
        }

        i32* result = new i32[2];

        result[0] = ( key[0][0] * plain[0] + key[0][1] * plain[1] ) % 26;
        result[1] = ( key[1][0] * plain[0] + key[1][1] * plain[1] ) % 26;

        return result;
    }

    void HILL_DECRYPT(i32 cipher[2], i32 key[][2]) {
        if (!cipher && !HILL_KEY_AVAILABLE(key)) {
            return;
        }

        i32 a, b;
        i32 pivot = ( key[0][0] * key[1][1] - key[0][1] * key[1][0] ) % 26;
        i32 rev_key[][2] = {
            { key[1][1], -key[0][1] },
            { -key[1][0], key[0][0] }
        };

        a = ( (cipher[0] * rev_key[0][0] + cipher[1] * rev_key[0][1]) / pivot ) % 26;
        b = ( (cipher[0] * rev_key[1][0] + cipher[1] * rev_key[1][1]) / pivot ) % 26;
        
        cipher[0] = NORMALIZE(a, 26);
        cipher[1] = NORMALIZE(b, 26);
    }
}
#endif
