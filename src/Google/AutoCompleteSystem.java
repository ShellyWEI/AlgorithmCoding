package Google;

import java.util.*;

class AutoCompleteSystem {
    TrieNode dummy = new TrieNode();
    TrieNode prevNode = dummy;
    StringBuilder sb = new StringBuilder();

    class TrieNode {
        Map<Character, TrieNode> next;
        Map<String, Integer> count;
        boolean isSentence;
        TrieNode() {
            next = new HashMap<>();
            count = new HashMap<>();
            isSentence = false;
        }
    }

    public AutoCompleteSystem(String[] sentences, int[] times) {
        int dataSize = sentences.length;
        for(int i = 0; i < dataSize; i++) {
            addSentenceCount(sentences[i], times[i]);
        }
    }
    private void addSentenceCount(String sentence, int time) {
        TrieNode cur = dummy;
        char[] charArray = sentence.toCharArray();
        for (int j = 0; j < charArray.length; j++) {
            TrieNode child = cur.next.get(charArray[j]);
            if (child == null) {
                child = new TrieNode();
                child.count.put(sentence, time);
            } else {
                // duplicated sentences
                if (child.count.containsKey(sentence)) {
                    child.count.put(sentence, child.count.get(sentence) + time);
                } else {
                    child.count.put(sentence, time);
                }

            }
            cur.next.put(charArray[j], child);
            if (j == charArray.length - 1) {
                child.isSentence = true;
            }
            cur = child;
        }
    }

    public List<String> input(char c) {
        List<String> result = new ArrayList<>();
        if (c == '#') {
            if (prevNode != null) {
                prevNode.isSentence = true;
            }
            addSentenceCount(sb.toString(), 1);
            prevNode = dummy;
            sb = new StringBuilder();
            return result;
        }
        sb.append(c);
        if (prevNode == null) {
            return result;
        }
        TrieNode curNode = prevNode.next.get(c);
        PriorityQueue<Map.Entry<String, Integer>> maxHeap = new PriorityQueue<>(3, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> m1, Map.Entry<String, Integer> m2) {
                if (m1.getValue().equals(m2.getValue())) {
                    return m1.getKey().compareTo(m2.getKey());
                }
                return m2.getValue() < m1.getValue() ? -1 : 1;
            }
        });
        // there is a match
        if (curNode != null) {
            for (Map.Entry<String, Integer> entry : curNode.count.entrySet()) {
                maxHeap.offer(entry);
            }
            for (int i = 0 ; i < 3; i++) {
                Map.Entry<String, Integer> topMax = maxHeap.poll();
                if (topMax != null) {
                    result.add(topMax.getKey());
                } else {
                    break;
                }
            }
        }
        prevNode = curNode;
        return result;
    }
    public static void main(String[] args) {
        //String[] sentences = new String[]{"uqpewwnxyqxxlhiptuzevjxbwedbaozz","ewftoujyxdgjtazppyztom","pvyqceqrdrxottnukgbdfcr","qtdkgdbcyozhllfycfjhdsdnuhycqcofaojknuqqnozltrjcabyxrdqwrxvqrztkcxpenbbtnnnkfhmebj","jwfbusbwahyugiaiazysqbxkwgcawpniptbtmhqyrlxdwxxwhtumglihrgizrczv","cfptjitfzdcrhw","aitqgitjgrcbacgnaasvbouqsqcwbyskkpsnigtfeecmlkcjbgduban","utsqkmiqqgglufourfdpgdmrkbippffacwvtkpflzrvdlkdxykfpkoqcb","ethtbdopotpamvrwuomlpahtveyw","jiaqkaxovsqtkpdjfbkajpvpyetuoqwnrnpjdhoojbsdvneecsdvgqpyurmsvcy","j","btbnuplyeuccjbernsfbnveillrwdbqledwvpmvdbcugkurrkabtpykhlcogeszclyfuquafouv","hndjzblegevtfkgbjttektox","gtvnlninpvenapyfgmsjdisfnmiktitrutctawosjflvzfkbegnprixzqwzcyhoovsivuwmofsveqkyosowuyamuvy","sawrirvrfrbfagreahrioaombukmdwztbpggnxd","mgdcwptvbvhzyvvumvbjjn","otjvvkegwleyyxtghwgfmlsqlhrlibdvqfinyyebotjpwoaejhtornfgikmifdmwswbqgwhcbzuhrpajxuqicegcptszct","zlondsttyvnnnnxjtoqnlktitwzurissczzbyfsbgpoawodwjpsmavaugnhqtsbeixwl","yehvdehbtmwqkmcjmvpivfzqvevkotwzvjoyfvp","bjximtpayjdcxbrnksbtfnpynzaygygdflowewprqngdadzdhxcpgapjejojrkzrutgcsfpfvpluagniqimfqddldxqiw","bysyrxfykivyauysytgxfhqcrxliulahuizjvozpywrokxujhzpauxwufcxiitukljiiclatfrspqcljjoxpxziumstnhqr","uxtvutlgqapyfltiulwrplesmtowzoyhhjhzihatpuvmutxqgxfawpwypedbz","jzgsdjdawrqfladolduldhpdpagmvllvzamypuqlrpbmhxxadqaqrqavtxeghcyysjynovkiyjtvdluttodtmtocajgttmv","mbijfkmepalhdiubposdksdmmttxblkodcdrxbnxaqebnwliatnxpwaohbwkidia","ljggggbyxwrwanhjonoramexdmgjigrtpz","cqfvkutpipxjepfgsufonvjtotwfxyn","kvseesjazssavispavchdpzvdhibptowhyrrshyntpwkez","nveuzbaosuayteiozmnelxlwkrrrjlwvhejxhupvchfwmvnqukphgoacnazuoimcliubvhv","uwrpwhfdrxfnarxqpkhrylkwiuhzubjfk","bniyggdcloefwy","ihranmhbsahqjxesbtmdkjfsupzdzjvdfovgbtwhqfjdddwhdvrnlyscvqlnqpzegnvvzyymrajvso","lscreasfuxpdxsiinymuzybjexkpfjiplevqcjxlm","uwgnfozopsygnmptdtmmuumahoungpkodwxrcvfymqpeymaqruayvqqgoddgbnhemtsjifhxwiehncswxzrghf","nyfbxgcpfrzyqwfjzgmhuohjhrjizsyjqgmertmooeiaadcmiuyyylpcosnweoyydeauazhzbeaqn","tpylrxbwnkrfxckfdlvrbytaezuzmyscpvruthuvbxjenkeolvqsrjqzizyclzmqtjvnamdansmzyspcfghfprorqprua","nhldlmxpuckxeekipkrzugatjiivtazjbjyxokksyueyjbgmrovbckbxqcqefaiavzsarbbypgmpxe","sylraqsd","xr","xkzpxkhrucyatpatkigvntohihibyisyqtkjdhatdvyvxbjttz","nvnz","blzddwxphkgqfsfzfclwytstpvpzgcdeggdwzukzirscfzcteeuqbmmrfxcnokbbyxkqrxtjfarcefiynwfmy","inuxmuhtdwpuvyludwtokhtalxbuccepsayrjycbcwbtnfholjvkmypodv","awwillrm","xznodxngrstjrwqzmlmigpw","khlxjdtictufdfbkgfusdtaaeuspbbfmtjodflgqofzlqnulkdztflm","nlngmckslyqzjiyiexbropbxnynjcstziluewypboqhqndqsxhtnosrgrameajovsclrgwqjgnztvxrkhwnxkfrf","yroadxhxyacaexrwju","ujxlbpcbxdqrvubifnpzjmmkolyljzjhdegaiowaal","tnfnjgtxbckbpyplucprxcqzhrfjimylmlhdglntfydepltjvklyxesndzuubienhvuaqc","ouedhtkpkg","ygchsrrubucqffewifsxaefwocfaiiupqbomktvrcddggqfgnaycstpccwtbheyaqwhosxajqeqqxzyjsfng","jqqgpjvfkgjh","csowoazaiyejgyixszqmtctpzlkccccqregyhtvxccvrpkupwcyhqatxscevzdfbdqnuyadiyfnhysddfyxpgqtjiogmxsmzbbkr","dlzxdpchkdaztkqtrjmuujgoiae","plcjkwukkyqluxjbhxsyeaqvviinfuujsafwsquidvmutsrukxwrv","yopqbtpoqhpcktjangauzcvvpephhprpaaclbbkgchlqkrwdsaupeizlwxzcpkchoagmrrkwdkthosmrjefgbumnrjsb"};
        //int[] times = new int[]{12,9,4,4,1,5,3,4,7,9,2,4,2,3,11,13,1,3,4,10,7,1,9,5,10,14,5,3,2,11,5,14,4,13,11,5,15,8,1,12,2,11,4,2,11,14,9,12,1,7,13,11,7,2,6,10};
        String[] sentences = new String[]{"i love you","island","iroman","i love leetcode"};
        int[] times = new int[]{5,3,2,2};
        AutoCompleteSystem auto = new AutoCompleteSystem(sentences, times);
        //List<String> result = new ArrayList<>();
        List<String> r1 = auto.input('i');
        List<String> r2 = auto.input(' ');
        List<String> r3 = auto.input('a');
        List<String> r4 = auto.input('#');

        List<String> r5 = auto.input('m');
        List<String> r6 = auto.input('p');
        List<String> r7 = auto.input('k');
        List<String> r8 = auto.input('q');
    }
}

/**
 * Your AutocompleteSystem object will be instantiated and called as such:
 * AutocompleteSystem obj = new AutocompleteSystem(sentences, times);
 * List<String> param_1 = obj.input(c);
 */