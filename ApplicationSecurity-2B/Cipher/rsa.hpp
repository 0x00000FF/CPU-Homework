#ifndef _RSA_
#define _RSA_

#include <cmath>
#include <exception>

namespace RSA {
    /* RSA Implementation
    * Implemented at June 11, 2018 with C/C++
    */

    using u32 = unsigned;
    using i64 = __int64;

    bool IS_PRIME(const i64 num) {
        i64 p = std::sqrt(num);

        for (u32 i = p; i >= 2; --i) {
            if (num % p == 0) return false;
         }

        return true;
    }

    i64 EUCLEDEAN_GCD(const i64 a, const i64 b) {
        if (b == 0)
            return a;

        return EUCLEDEAN_GCD(b, a % b);
    }

    i64 EULER_TOTIENT(const i64 N) {
        i64 count = 0;

        for (u32 i = 1; i < N; ++i) {
            if (EUCLEDEAN_GCD(N, i) == 1) ++count;
        }

        return count;
    }

    i64 EULER_TOTIENT(const i64 p, const i64 q) {
        return (p - 1) * (q - 1);
    }

    i64 POW(const i64 base, const i64 exp) {
        if (base == 0) return 1;
        else if (exp == 1) return base;
        else {
            if (exp % 2) {
                return base * POW(base * base, exp / 2);
            }
            else {
                return base * POW(base, exp - 1);
            }
        }
    }

    class RSA {
        i64 e, d, N;

    public:
        RSA() = default;

        void CreateKey(const i64 _p, const i64 _q, const i64 _e);
        i64 Encrypt(const i64 M);
        i64 Decrypt(const i64 C, const i64 _d);
    };

    void RSA::CreateKey(const i64 _p, const i64 _q, const i64 _e) {
        if (_p == _q || !IS_PRIME(_p) || !IS_PRIME(_q))
            throw std::exception("p or q is invalid");

        N = _p * _q;
        e = _e;

        auto totient = EULER_TOTIENT(_p, _q);
        
        if (_e > totient)
            throw std::exception("public key cannot be larger than fi(N)");
        else if (EUCLEDEAN_GCD(totient, _e) != 1)
            throw std::exception("public key must be coprime for fi(N)");

        for (d = 1; ; ++d) {
            if (((e * d) % totient) == 1)
                return;
        }
    }

    i64 RSA::Encrypt(const i64 M) {
        return POW(M, e) % N;
    }

    i64 RSA::Decrypt(const i64 C, const i64 _d) {
        if (d != _d)
            throw std::exception("private key does not match!");

        return POW(C, d) % N;
    }
}

#endif
