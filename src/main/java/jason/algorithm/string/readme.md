Understand KMP
Understand rolling hash
Understand Finite Automate
Understand Trie/suffix tree


# Rabin-Karp: hash pattern and text substring
**Reference**
  * http://www.geeksforgeeks.org/searching-for-patterns-set-3-rabin-karp-algorithm/
  * http://www.cs.princeton.edu/courses/archive/fall04/cos226/lectures/string.4up.pdf
  * https://en.wikipedia.org/wiki/Rabin%E2%80%93Karp_algorithm

**Use modulus Properties**
  
  1. Modular multiplication: (A * B) mod C = (A mod C * B mod C) mod C
  2. Modular addition: (A + B) mod C = (A mod C + B mod C) mod C
  3. Modular exponenietion: (A^B) mod c=((A mod C)^B) mod C
  
   use the number of character in charset as radix. For ASCII, the radix is 256. 
   To avoid overflow, we take result % a prime. It is better to use a big prime so that there is a less collision.
   The rolling hash formula.
   (( **PreviousValue** - Text[i]*(Radix^(PatternLen-1)))*Radix + text[i+M])%Prime
   =(PreviousValue*Radix -Text[i]*Radix^PatternLen + text[i+M])%Prime
   =((PreviousValue*Radix)%Prime -(Text[i]*Radix^PatternLen)%Prime +text[i+m]%prime)%prime
   =((PreviousValue%prime * Radix%prime)%Prime
    -(Text[i]%prime * (Radix^PatternLen)%Prime)%prime
    +text[i+m]%prime
    )%prime
   =((**PreviousHash** * Radix%prime)%Prime                                          Radix <prime, text[i]<prime
    -(Text[i]%prime * (Radix^PatternLen)%Prime)%prime
    +text[i+m]%prime
    )%prime
  = ((**PreviousHash** * Radix)%Prime + **Prime**
    -(Text[i]%prime * (Radix^PatternLen)%Prime)%prime
    +text[i+m]%prime
    )%prime
  =((**PreviousHash** * Radix)%Prime + **Prime**
    -(Text[i] * (Radix^PatternLen)%Prime)%prime
    +text[i+m]
    )%prime
  **Radix^PatternLen%Prime** Can be precomputed
  
#KMP BoryeMoore, Sunday, Hoorspool
**shift** what is shift. Shift is window slide. Its purpose is to find an index at patten so substring pattern[0...j] will match that of text[i-j, i]. During shift process, j decreases

+ KMP: http://www.iti.fh-flensburg.de/lang/algorithmen/pattern/kmpen.htm
