package kr.co.mystockhero.geotogong.data;

import kr.co.mystockhero.geotogong.R;
import kr.co.mystockhero.geotogong.common.CommonUtil;

/**
 * Created by Administrator on 2017-01-06.
 */
public class MasterFixedData {
    public static String[] name = { "이철용", "알렉산드르 드미트리", "앤드류 웨인", "리차드 테일러", "해리 브라잇슨", "피터 그로스", "지오반니 리코", "알버트리"};
    public  String[] realMasterNames = {"벤저민 그레이엄", "조셉 피오트로스키", "존 네프", "케네스 피셔", "앤드비욘드투자자문", "피터 린치", "그레이엄 + 피터 린치", "준비중"};
    public int[] id = { 31, 32, 33, 34, 35, 36, 37, 38 };
    public double[] discountRatio = { 1, 0.0, 0.5, 0.5, 0.0, 0.5, 0.0, 0.0 };
    public double basePrice = 72000;
    public String[] masterStrategy = {
            "거장의 스타일은 큰 시세 차익보다는 원금을 우선시하는 스타일로 저위험을 추구하였습니다.\n" +
                    "\n특히 거장은 <font color='#' size='28'><big><b>장기투자에 적합</b></big></font>한 전략과 종목을 제시하였는데요. 이를 위해서 거장은 저평가 되어 있는 회사를 찾으려고 하였으며 " +
                    "특히 <big><b>내재가치의 개념</b></big>을 사용하여 싸거나 이에 가까운 가격에 거래되고 있는 회사에 투자하였습니다.\n" +
                    "\n<big><font color='#0068e8' size='30'><b>저평가 그리고 적절한 재무건전성</b></font></big>이 바로 거장이 추구하였던 전략이라고 할 수 있겠습니다.",
            "거장은 장부가액보다 저평가되고 있는 회사에 투자하라 하였습니다. 그러면서도 어느 정도 성장성 역시 확보되고 재무건전성도 양호해야 한다고 했습니다.\n" +
                    "\n특히 거장은 <big><b>자산효율성</b></big>을 강조하며 급등주가 될 기업을 고르는 일에 몰두했었는데요.\n" +
                    "\n결국 <font color='#0068e8' size='30'><big><b>저평가, 성장성, 재무건전성</b></big></font> 등 지극히 상식에 기반한 쉬운 투자를 구현하는 스타일이라 생각됩니다.",
            "거장은 <font color='#0068e8' size='30'><big><b>시장평균보다 저평가되어 있는 회사</b></big></font>를 발굴하려고 했습니다. 특히 거장은 사람들이 피하는 주식에 집착하는 경향을 보였는데요, 이 때문에 거장은 극단적인 가치투자자라는 별명을 얻기도 했습니다." +
                    "\n하지만 저평가되어 있는 기업 중에는 다 쓰러져 가거나 경영이 <big><b>부실해서 주가가 싼 경우</b></big>도 있기 때문에 이를 <big><b>조심</b></big>해야 한다고 조언하고 있습니다." +
                    "\n한편 거장은 뛰어난 직관의 소유자이기도 하지만 저평가된 기업을 발굴하기 위해서 꽤 엄격한 <big><b>정량적인</b></big> 투자기법을 고집했다고도 알려져 있습니다.",
            "거장은 대표적인 성장투자자로 <font color='#0068e8' size='30'><big><b>매출과 시장지배력을 중시</b></big></font>하였습니다. 특히 거장은 경기순환주와 비경기순환주를 나누어 판단했는데요. " +
                    "경기순환주의 경우 엄격하게 판단하여 매출액 대비 주가가 싸야지만 투자하였고, 비경기순환주의 경우에는 어느정도 기준을 완화시켰습니다.\n" +
                    "\n동시에 거장은 독특하게도 <big><b> 역시 저평가된 회사가 있을 수 있다</b></big>면서 성장과 가치를 함께 고려한다고 알려져 있습니다.\n" +
                    "\n결국, 시장지배력을 가지고 성장하지만 여전히 <big><b>소외</b></big>되어 있는 회사… 바로 이것이 거장이 추구하는 투자전략이라고 생각됩니다",
            "마이크로캡의 대가라 불리는 거장은 말 그대로 <big><b>초소형주만</b></big>으로 고수익을 추구하고 있습니다. " +
                    "특히 금융분야에서 여러 트랙에 걸친 경험과 직관으로 완성한 <big><b>新마법공식</b></big>을 활용하여 쉬운 투자를 추구하는데요. \n" +
                    "\n이렇듯 공식에 기반한 투자를 진행하므로 종목 선정이나 편입 제외 시에 감정에 의한 흔들림이 없으며 일관성이 보장된다는 장점이 있습니다.\n" +
                    "\n다만 그 공식이 무엇인지는 철저히 비밀에 붙여지고 있답니다.\n어린 손자를 위해 개발한 <font color='#0068e8' size='30'><big><b>초소형주 상승 패턴 공식!</b></big></font>\n" +
                    "그 공식이 무엇인지는 모르지만 우리는 종목 수익률과 변동성으로 즉 누적되어가는 실적으로 거장을 충분히 믿을 수 있을 것입니다.",
            "프리즘 성장투자의 창시자이자 완성자인 거장은 <big><b>중위험을 추구</b></big>했습니다. 즉 적절한 위험을 떠안고 <font color='#0068e8' size='30'><big><b>고성장주에 초점</b></big></font>을 맞춘 것입니다.\n" +
                    "\n하지만 거장은 아무리 뛰어난 투자자라 하여도 주식시장을 단기적으로 예측하기는 힘들다는 점을 강조하며 <big><b>장기투자</b></big>를 지향하였습니다.\n" +
                    "\n성장하는 기업의 다양한 측면을 심도있게 연구하였기에 거장의 종목은 <big><b>저평가와 고평가를 가리지 않았으며</b></big> 특히 남들이 보지 못한 성장하는 기업의 특징을 미리 포착하는데 탁월한 능력을 보였습니다.",
            "<font color='#0068e8' size='30'><big><b>거장은 가치투자와 성장투자 모두를 아우를 수 있는 포트폴리오</b></big></font>를 구성하고 있습니다.\n" +
                    "\n먼저 가치투자 쪽에서는 벤자민 그레이엄의 투자 원리를 계승하여 <big><b>자산대비 저평가</b></big> 되어 있는 주식을 우선적으로 스크리닝하고 있으며 이후에는 해당 기업이 보유한 자산을 활용해 이를 얼마나 이익으로 실현시키고 있는지 그 " +
                    "<big><b>효율성을 검토</b></big>함으로써 전통적 가치투자에서 진일보한 분석법을 활용하고 있습니다.\n" +
                    "\n한편 성장투자 쪽에서는 피터 린치의 투자 원리를 계승했는데, 거장은 <big><b>매출의 측면에서 성장을 해석</b></big>하여 지속적으로 매출이 성장하는 기업은 우수한 경영자의 지도아래 결국 이익이 나게 되어 있다며 적절한 통제장치를 보유한 기업의 매출은 믿을만하다는 관점을 취하고 있습니다. \n"+
                    "\n이 외에 거장은 오를 만한 이유가 있는 기업은 언젠가는 반드시 오른다는 믿음이 필요하다고 주장하며 <big><b>오를 만한 주식을 미리 사놓고 기다리는 것</b></big>이야말로 가장 완벽한 전략이라 조언하고 있습니다. \n",
            "준비중"
    };

    public int[][] rolemodelImages = {
            { R.drawable.rolemodel_03, R.drawable.rolemodel_02},
    { R.drawable.rolemodel_04, R.drawable.rolemodel_06},
        { R.drawable.rolemodel_05, R.drawable.rolemodel_06},
            { R.drawable.rolemodel_07, R.drawable.rolemodel_06},
                { R.drawable.rolemodel_01, R.drawable.rolemodel_08},
                    {R.drawable.rolemodel_09,R.drawable.rolemodel_02},
                        {R.drawable.rolemodel_03, R.drawable.rolemodel_09}
            };

    public String getMasterName(int _id) {
        for(int i=0; i<id.length; i++) {
            if( id[i] == _id ) {
                return name[i];
            }
        }
        return "";
    }

    public double getDicountRatio(int masterId) {
        for(int i=0; i<id.length; i++) {
            if( masterId == id[i]) {
                return discountRatio[i]*100;
            }
        }
        return 0.0;
    }

    public String getMasterPrice(int masterId) {
        String result = "";

        for(int i=0; i<id.length; i++) {
            if (masterId == id[i]) {
                result = CommonUtil.moneyFormat(basePrice * (1.0 - discountRatio[i]));
            }
        }
        return result;
    }
}
