// 3. Longest Substring Without Repeating Characters
// https://leetcode.com/problems/longest-substring-without-repeating-characters/description/
public int lengthOfLongestSubstring(String s) {
        int i_ptr = 0;
        int j_ptr = 0;
        int max = 0;
        HashSet<Character> set = new HashSet<>();
        while(j_ptr<s.length()) {
            if(!set.contains(s.charAt(j_ptr))) {
                set.add(s.charAt(j_ptr));
                j_ptr++;
                max = Math.max(set.size(),max);
            } else {
                set.remove(s.charAt(i_ptr));
                i_ptr++;
            }
        }
        return max;
    }
