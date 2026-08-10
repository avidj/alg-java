# Binary stream specification

Specification for `helpers.BinaryInputStream` and `helpers.BinaryOutputStream`: a pair of classes
that provide bitwise reading and writing over arbitrary `java.io` streams. This document was
derived from the public API of the previous (removed) classes and from the observable behaviour
required by their only consumer, `org.avidd.compression.Huffman`, and its tests. The
implementation in `src/main/java/helpers/` is written against this specification, not against the
removed code.

## Scope

The previous classes carried a number of methods marked `@deprecated remove` that no code in this
repository used (`readString`, `readShort`, `readLong`, `readDouble`, `readFloat`, `readByte`,
`readInt(int)`, and the corresponding writers). They are dropped. The specified API is the set of
methods actually exercised by `Huffman` and the compression tests, plus the obvious symmetric
counterparts.

## Bit and byte ordering

Both classes use the same ordering rules; they are what makes a write/read round trip work:

1. **Bits within a byte are MSB-first.** The first bit written to a fresh byte becomes bit 7 (the
   most significant bit) of that byte; the eighth bit written becomes bit 0. Reading is symmetric:
   the first bit read from a byte is bit 7.
2. **Multi-byte values are big-endian.** A 32-bit `int` is written as bits 31 down to 0, i.e. the
   most significant byte first.
3. **An `r`-bit value is the `r` low-order bits of the argument, most significant of those `r`
   bits first.** So `write('A', 8)` emits the bits `01000001` in that order.

Consequence: writing a sequence of whole bytes with `write(char, 8)` and reading them back with
`readChar(8)` produces the identical byte sequence on the underlying streams — bitwise access
degenerates to plain byte I/O when everything is byte-aligned.

## `BinaryOutputStream`

Wraps a `java.io.OutputStream`. Maintains an 8-bit accumulation buffer; whenever 8 bits have
accumulated, the completed byte is written to the underlying stream. Construction performs no I/O.

| Method | Behaviour |
|---|---|
| `BinaryOutputStream(OutputStream)` | Wrap the given stream. No I/O. |
| `write(boolean bit)` | Append one bit; `true` = 1, `false` = 0. |
| `write(char x)` | Equivalent to `write(x, 8)`. Throws `IllegalArgumentException` if `x > 255`. |
| `write(char x, int r)` | Append the `r` low-order bits of `x`, most significant first. Throws `IllegalArgumentException` unless `1 <= r <= 16` and `x < 2^r`. |
| `write(int x)` | Append all 32 bits of `x`, most significant first. Works for any `int` value, including negative values. |
| `write(int x, int r)` | Append the `r` low-order bits of `x`, most significant first. Throws `IllegalArgumentException` unless `1 <= r <= 32`; for `r < 32`, requires `0 <= x < 2^r`. `write(x, 32)` accepts any `int`. |
| `flush()` | If a partial byte is buffered, pad its remaining low-order bits with 0s and write it, then flush the underlying stream. The buffer is empty afterwards. |
| `close()` | `flush()`, then close the underlying stream. |

### Padding semantics

`flush()` is how a bit stream ends: the final partial byte is zero-padded on the right (the
low-order bits). Padding is indistinguishable from data zero-bits on the reading side, so a
consumer that does not end on a byte boundary must carry its own length information — `Huffman`
does this by writing the character count as a 32-bit `int` before the code bits. Calling `flush()`
mid-stream inserts padding at that point and is therefore only meaningful as a terminal operation
(or between independent byte-aligned sections).

Bits written after a `flush()` start a fresh byte.

## `BinaryInputStream`

Wraps a `java.io.InputStream`. Maintains a one-byte read buffer with a bit cursor. The
constructor eagerly reads the first byte (hence `throws IOException`) so that end-of-stream is
always known one byte in advance; each subsequent refill happens when the current byte's bits are
exhausted.

| Method | Behaviour |
|---|---|
| `BinaryInputStream(InputStream)` | Wrap the given stream and pre-read the first byte. |
| `isEmpty()` | `true` iff every bit of the stream has been consumed (the pre-read hit end of stream and no buffered bits remain). Performs no I/O and does not consume anything. |
| `readBit()` | Return the next bit as a `boolean` (`true` = 1). Throws `EOFException` if the stream is empty. |
| `readChar()` | Equivalent to `readChar(8)`. |
| `readChar(int n)` | Return the next `n` bits as a `char`, first bit read = most significant of the `n` bits. Throws `IllegalArgumentException` unless `1 <= n <= 16`; throws `EOFException` if fewer than `n` bits remain. |
| `readInt()` | Return the next 32 bits as an `int`, first bit read = bit 31. Throws `EOFException` if fewer than 32 bits remain. |
| `close()` | Close the underlying stream. |

### End-of-stream semantics

`isEmpty()` is the only non-throwing way to detect the end. The loop idiom used by `Huffman` must
work:

```java
while (!in.isEmpty()) {
    char c = in.readChar(8);   // reads exactly the bytes of the stream, then isEmpty() is true
    ...
}
```

For a stream of `k` whole bytes this loop executes exactly `k` times. A read that would need more
bits than remain throws `EOFException` after which the stream state is unspecified (only `close()`
may be called).

## Round-trip properties

These are the conformance properties tested in `src/test/java/helpers/BinaryStreamTest.java`:

1. Any sequence of bits written via `write(boolean)` and flushed is read back identically via
   `readBit()`, followed by 0-valued padding bits up to the next byte boundary.
2. Any value round-trips through the matching write/read pair: `write(int)`/`readInt()` for all
   `int` values, `write(char, r)`/`readChar(r)` for all `0 <= x < 2^r`, `1 <= r <= 16`.
3. Mixed sequences (bits, sub-byte groups, chars, ints, interleaved in any order) round-trip as
   long as reads mirror writes in order and width.
4. Reading a written stream bit-by-bit and reading it in multi-bit groups yield the same bit
   sequence: for byte-aligned data, `readChar(8)` agrees with 8 × `readBit()`.
5. The encoding of byte-aligned data is the identity: `write((char) b, 8)` for bytes `b0..bk`
   followed by `flush()` produces exactly `b0..bk` on the underlying stream.
